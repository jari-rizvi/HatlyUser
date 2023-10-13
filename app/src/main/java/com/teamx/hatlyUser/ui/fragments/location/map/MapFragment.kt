package com.teamx.hatlyUser.ui.fragments.location.map

import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatCheckedTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.JsonObject
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.constants.NetworkCallPointsNest
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentMapBinding
import com.teamx.hatlyUser.ui.fragments.location.map.bottomSheetAddSearch.BottomSheetAddressFragment
import com.teamx.hatlyUser.ui.fragments.location.map.bottomSheetAddSearch.BottomSheetListener
import com.teamx.hatlyUser.ui.fragments.location.map.bottomSheetAddressDetail.BottomSheetAddressDetailFragment
import com.teamx.hatlyUser.utils.LocationPermission
import com.teamx.hatlyUser.utils.PrefHelper
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import java.io.IOException
import java.util.Locale


@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding, MapViewModel>(), OnMapReadyCallback,
    BottomSheetListener {

    override val layoutId: Int
        get() = R.layout.fragment_map
    override val viewModel: Class<MapViewModel>
        get() = MapViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private var mapFragment: SupportMapFragment? = null
    private lateinit var txtBottomLocation: TextView
    private lateinit var imgLabelOther: AppCompatCheckedTextView
    private lateinit var inpOtherLabel: EditText

    lateinit var googleMap: GoogleMap
    private var isMapBeingDragged = true
    private lateinit var bottomSheetAddSearchFragment: BottomSheetAddressFragment
    private lateinit var bottomSheetAddressFragment: BottomSheetAddressDetailFragment

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private var myJob: Job? = null

    private var latLngFinal: LatLng? = null

    private var apartmentStr = ""
    private var buildingNumStr = ""
    private var addDirectionStr = ""
    private var addressLabel = "Home"

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }

        val txtConfirmLocation1: TextView = view.findViewById(R.id.txtConfirmLocation1)
        val imgLabelHome: AppCompatCheckedTextView = view.findViewById(R.id.imgLabelHome)
        val imgLabelWork: AppCompatCheckedTextView = view.findViewById(R.id.imgLabelWork)
        imgLabelOther = view.findViewById(R.id.imgLabelOther)
        inpOtherLabel = view.findViewById(R.id.inpOtherLabel)
        txtBottomLocation = view.findViewById(R.id.txtBottomLocation)
        val inpApartmentNum: EditText = view.findViewById(R.id.inpApartmentNum)
        val inpBuildingNum: EditText = view.findViewById(R.id.inpBuildingNum)
        val inpAddDirectionNum: EditText = view.findViewById(R.id.inpAddDirectionNum)

        val bottomSheetCons: ConstraintLayout = view.findViewById(R.id.bottomSheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetCons)

        mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        latLngFinal = LatLng(0.0, 0.0)

        sharedViewModel.locationmodel.observe(requireActivity()) { locationModel ->

            when (locationModel.isAction) {
                "update" -> {
                    isMapBeingDragged = false
                    when (locationModel.label) {
                        "Home" -> {
                            addressLabel = "Home"
                            imgLabelHome.isChecked = true
                            imgLabelWork.isChecked = false
                            imgLabelOther.isChecked = false
                            inpOtherLabel.visibility = View.GONE
                        }

                        "Work" -> {
                            addressLabel = "Work"
                            imgLabelHome.isChecked = false
                            imgLabelWork.isChecked = true
                            imgLabelOther.isChecked = false
                            inpOtherLabel.visibility = View.GONE
                        }

                        else -> {
                            imgLabelHome.isChecked = false
                            imgLabelWork.isChecked = false
                            imgLabelOther.isChecked = true
                            inpOtherLabel.visibility = View.VISIBLE
                        }
                    }

                    latLngFinal = LatLng(locationModel.lat, locationModel.lng)
                    latLngFinal?.let { updateMap(it) }
                }

                "add" -> {

                }
                else -> {
                    requestLocation()
                }
            }
        }




        mViewDataBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
//            requireActivity().finish()
        }

        mViewDataBinding.txtEnterLocaion.setOnClickListener {
            findNavController().navigate(R.id.action_allowLocationocationFragment_to_homeFragment)
        }

        bottomSheetAddSearchFragment = BottomSheetAddressFragment()
        bottomSheetAddSearchFragment.setBottomSheetListener(this)

        bottomSheetAddressFragment = BottomSheetAddressDetailFragment()

        mViewDataBinding.imgEditAddress.setOnClickListener {

            if (!bottomSheetAddSearchFragment.isAdded) {
                bottomSheetAddSearchFragment.show(
                    parentFragmentManager,
                    bottomSheetAddSearchFragment.tag
                )
            }
        }

        mViewDataBinding.txtConfirmLocation.setOnClickListener {

            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

            if (!bottomSheetAddressFragment.isAdded) {
//                bottomSheetAddressFragment.show(parentFragmentManager, bottomSheetAddressFragment.tag)
            }

        }

        txtConfirmLocation1.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

            apartmentStr = inpApartmentNum.text.toString().trim()
            buildingNumStr = inpBuildingNum.text.toString().trim()
            addDirectionStr = inpAddDirectionNum.text.toString().trim()

            mViewModel.createAddress(createJson())
        }

        imgLabelHome.setOnClickListener {
            addressLabel = "Home"
            imgLabelHome.isChecked = true
            imgLabelWork.isChecked = false
            imgLabelOther.isChecked = false
            inpOtherLabel.visibility = View.GONE
        }

        imgLabelWork.setOnClickListener {
            addressLabel = "Work"
            imgLabelHome.isChecked = false
            imgLabelWork.isChecked = true
            imgLabelOther.isChecked = false
            inpOtherLabel.visibility = View.GONE
        }

        imgLabelOther.setOnClickListener {
            imgLabelHome.isChecked = false
            imgLabelWork.isChecked = false
            imgLabelOther.isChecked = true
            inpOtherLabel.visibility = View.VISIBLE
        }

        if (!mViewModel.createAddressResponse.hasActiveObservers()) {
            mViewModel.createAddressResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            if (data.address.isNotEmpty()) {
                                findNavController().navigate(R.id.action_mapFragment_to_homeFragment)
                            }
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        mViewDataBinding.root.snackbar(it.message!!)
                    }
                }
            }
        }

    }


    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            // Handle the location result
            if (location != null) {

                val latLng = LatLng(location.latitude, location.longitude)

                Log.e("requestLocation", "latitude, ${latLng.latitude}")
                Log.e("requestLocation", "longitude, ${latLng.longitude}")

                updateMap(latLng)
                // Do something with latitude and longitude
            } else {
                // Location is null, handle accordingly
            }
        }.addOnFailureListener { exception: Exception ->
            // Handle exceptions
            Log.e("requestLocation", "Error getting location, ${exception.message}")
        }
    }

    @SuppressLint("MissingPermission")
    private fun updateMap(latLng: LatLng) {

        googleMap.uiSettings.isZoomControlsEnabled = true
//        googleMap.uiSettings.isScrollGesturesEnabled = false
        googleMap.uiSettings.isRotateGesturesEnabled = true
        googleMap.isMyLocationEnabled = true
//        googleMap.uiSettings.isTiltGesturesEnabled = false
        googleMap.uiSettings.isZoomGesturesEnabled = true
//        googleMap.uiSettings.isMapToolbarEnabled = false


        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))

//        handler.post(myRunnable)
//        getAddressFromLocation(latLng)


    }

    private fun getAddressFromLocation(latLng: LatLng): String {

        var addressStr = ""

        val geocoder = Geocoder(requireActivity(), Locale.getDefault())

        try {
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

            if (addresses?.isNotEmpty() == true) {
                val address = addresses[0]
                val addressLine = address.getAddressLine(0)
                val city = address.locality
                val state = address.adminArea
                val country = address.countryName
                val postalCode = address.postalCode


                addressStr = "$addressLine, $city\n$state, $country, $postalCode"

//                mViewDataBinding.txtShowAddress.text = try {
//
//                } catch (e: Exception) {
//                    ""
//                }

                // Do something with the address information
                Log.d("requestLocation", "addresses: $addresses")
            } else {
                // No address found
                Log.d("requestLocation", "No address found for the given location")
//                mViewDataBinding.txtShowAddress.text = "No address found for the given location"
                addressStr = "No address found for the given location"
            }
        } catch (e: IOException) {
            // Handle IOException
            Log.e("requestLocation", "Error getting address", e)
        }
        return addressStr
    }


    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0

//        googleMap.setOnMapClickListener { latLng ->
//            // Handle map click
//            // Set the flag to indicate that this movement is user-initiated
//            isMapBeingDragged = true
//            // Perform other actions as needed
//        }

        googleMap.setOnCameraIdleListener {

            Log.d("setOnCameraIdleListener", "onMapReady: working")

            myJob?.cancel()

            myJob = GlobalScope.launch(Dispatchers.Main) {

                if (isMapBeingDragged) {

                    val currentLatLng = googleMap.cameraPosition.target
                    mViewDataBinding.locationLoader.visibility = View.VISIBLE
                    val result = withContext(Dispatchers.IO) {
                        getAddressFromLocation(currentLatLng)
                    }
                    latLngFinal = currentLatLng
                    mViewDataBinding.locationLoader.visibility = View.GONE
                    mViewDataBinding.txtShowAddress.text = result
                    txtBottomLocation.text = result
//                handler.removeCallbacks(myRunnable)
//                handler.postDelayed(myRunnable, 200)
                }
                isMapBeingDragged = true
            }


        }
    }


//    private val handler = Handler(Looper.getMainLooper())
//
//    private val myRunnable = Runnable {
//        if (isMapBeingDragged) {
//            val currentLatLng = googleMap.cameraPosition.target
//            getAddressFromLocation(currentLatLng)
//        }
//        isMapBeingDragged = true
//    }


    override fun onBottomSheetDataReceived(data: String, latLng: LatLng) {
        isMapBeingDragged = false
        mViewDataBinding.txtShowAddress.text = data
        txtBottomLocation.text = data
        bottomSheetAddSearchFragment.dismiss()

        latLngFinal = latLng

        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng), 1000, null)

//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))

        Log.e("requestLocation", "data, $data")
        Log.e("requestLocation", "latLng, $latLng")
    }

    private fun createJson(): JsonObject {
        val params = JsonObject()
        try {
            if (mViewDataBinding.txtShowAddress.text.isNotEmpty()) {
                params.addProperty("address", mViewDataBinding.txtShowAddress.text.toString())
            }

            if (imgLabelOther.isChecked) {
                addressLabel = inpOtherLabel.text.toString().trim()
            }

            if (addressLabel.isNotEmpty()) {
                params.addProperty("label", addressLabel)
            }

            val lat = latLngFinal?.latitude ?: 0.0
            if (lat != 0.0) {
                params.addProperty("lat", lat)
            }
            val lng = latLngFinal?.longitude ?: 0.0
            if (lng != 0.0) {
                params.addProperty("lng", lng)
            }

            if (apartmentStr.isNotEmpty()) {
                params.addProperty("apartmentNumber", apartmentStr.toInt())
            }
            if (buildingNumStr.isNotEmpty()) {
                params.addProperty("building", buildingNumStr)
            }
            if (addDirectionStr.isNotEmpty()) {
                params.addProperty("additionalDirection", addDirectionStr)
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return params
    }
}