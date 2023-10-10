package com.teamx.hatlyUser.ui.fragments.location.map

import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentMapBinding
import com.teamx.hatlyUser.ui.fragments.auth.login.LoginViewModel
import com.teamx.hatlyUser.ui.fragments.location.map.bottomSheet.BottomSheetAddressFragment
import com.teamx.hatlyUser.ui.fragments.location.map.bottomSheet.BottomSheetListener
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.Locale

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding, LoginViewModel>(), OnMapReadyCallback,
    BottomSheetListener {

    override val layoutId: Int
        get() = R.layout.fragment_map
    override val viewModel: Class<LoginViewModel>
        get() = LoginViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private var mapFragment: SupportMapFragment? = null

    lateinit var googleMap: GoogleMap
    private var isMapBeingDragged = true
    private lateinit var bottomSheetFragment: BottomSheetAddressFragment

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

        mapFragment =
            childFragmentManager.findFragmentById(com.teamx.hatlyUser.R.id.mapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        mViewDataBinding.imgBack.setOnClickListener {
            requireActivity().finish()
        }

        mViewDataBinding.txtEnterLocaion.setOnClickListener {
            findNavController().navigate(R.id.action_allowLocationocationFragment_to_homeFragment)
        }

        bottomSheetFragment = BottomSheetAddressFragment()
        bottomSheetFragment.setBottomSheetListener(this)


        mViewDataBinding.imgEditAddress.setOnClickListener {

            if (!bottomSheetFragment.isAdded) {
                bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
            }
        }

        mViewDataBinding.txtConfirmLocation.setOnClickListener {
            findNavController().navigate(R.id.action_mapFragment_to_homeFragment)
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

        getAddressFromLocation(latLng)


    }

    private fun getAddressFromLocation(latLng: LatLng) {


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


                mViewDataBinding.txtShowAddress.text = try {
                    "$addressLine, $city\n$state, $country, $postalCode"
                } catch (e: Exception) {
                    ""
                }

                // Do something with the address information
                Log.d("requestLocation", "addresses: $addresses")
            } else {
                // No address found
                Log.d("requestLocation", "No address found for the given location")
                mViewDataBinding.txtShowAddress.text = "No address found for the given location"
            }
        } catch (e: IOException) {
            // Handle IOException
            Log.e("requestLocation", "Error getting address", e)
        }

    }


    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        requestLocation()
//        googleMap.setOnMapClickListener { latLng ->
//            // Handle map click
//            // Set the flag to indicate that this movement is user-initiated
//            isMapBeingDragged = true
//            // Perform other actions as needed
//        }

        googleMap.setOnCameraIdleListener {
            if (isMapBeingDragged) {
                val currentLatLng = googleMap.cameraPosition.target
                getAddressFromLocation(currentLatLng)
            }
            isMapBeingDragged = true
        }
    }

    override fun onBottomSheetDataReceived(data: String, latLng: LatLng) {
        isMapBeingDragged = false
        mViewDataBinding.txtShowAddress.text = data
        bottomSheetFragment.dismiss()

        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng), 1000, null)

//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))

        Log.e("requestLocation", "data, $data")
        Log.e("requestLocation", "latLng, $latLng")
    }
}