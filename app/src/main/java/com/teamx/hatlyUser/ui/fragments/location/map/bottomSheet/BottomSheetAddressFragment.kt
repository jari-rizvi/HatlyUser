package com.teamx.hatlyUser.ui.fragments.location.map.bottomSheet

import android.Manifest
import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.EditText
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentAllowLocationBinding
import com.teamx.hatlyUser.databinding.FragmentMapBinding
import com.teamx.hatlyUser.ui.fragments.auth.login.LoginViewModel
import com.teamx.hatlyUser.ui.fragments.location.map.adapter.AddressListAdapter
import com.teamx.hatlyUser.ui.fragments.products.adapter.interfaces.ProductPreviewInterface
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.Locale
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class BottomSheetAddressFragment : BottomSheetDialogFragment(), ProductPreviewInterface {


    private lateinit var placesClient: PlacesClient
    private lateinit var addressEditText: EditText
    private lateinit var recAddress: RecyclerView
    private lateinit var addressListAdapter: AddressListAdapter
    private lateinit var addressArrayList: ArrayList<AutocompletePrediction>

    private var bottomSheetListener: BottomSheetListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet_address, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Places.initialize(requireActivity(), "AIzaSyAnLo0ejCEMH_cPgZaokWej4UdgyIIy5HI")
        placesClient = Places.createClient(requireActivity())

        addressEditText = view.findViewById(R.id.inpSearch)
        recAddress = view.findViewById(R.id.recAddress)

        addressArrayList = ArrayList()

        addressListAdapter = AddressListAdapter(addressArrayList, this)
        recAddress.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        recAddress.adapter = addressListAdapter

//        bottomSheetBehavior.peekHeight = 300

        addressEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not used
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not used
            }

            override fun afterTextChanged(s: Editable?) {
                // Fetch autocomplete predictions when the user types
                fetchAutocompletePredictions(s.toString())
            }
        })

    }


    private fun fetchAutocompletePredictions(query: String) {
        val token = AutocompleteSessionToken.newInstance()

        val request = FindAutocompletePredictionsRequest.builder()
            .setSessionToken(token)
            .setQuery(query)
            .build()

        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response ->
                handleAutocompletePredictions(response.autocompletePredictions)
            }
            .addOnFailureListener { exception ->

            }
    }

    private fun handleAutocompletePredictions(predictions: List<AutocompletePrediction>) {

        addressArrayList.clear()
        if (predictions.isNotEmpty()) {
            addressArrayList.addAll(predictions)
        }
        addressListAdapter.notifyDataSetChanged()

    }

    private fun getLatLngFromPrediction(prediction: AutocompletePrediction) {
        val placeId = prediction.placeId
        val request = FetchPlaceRequest.builder(placeId, listOf(Place.Field.LAT_LNG)).build()

        placesClient.fetchPlace(request)
            .addOnSuccessListener { response ->
                val place = response.place
                bottomSheetListener?.onBottomSheetDataReceived(prediction.getFullText(null).toString(),place.latLng)
            }
            .addOnFailureListener { exception ->
                Log.e("requestLocation", "\nFailed to get LatLng: ${exception.message}")
            }
    }

    fun setBottomSheetListener(listener: BottomSheetListener) {
        bottomSheetListener = listener
    }

    override fun clickRadioItem(requiredVarBox: Int, radioProperties: Int) {

    }

    override fun clickCheckBoxItem(optionalVeriation: Int) {

    }

    override fun clickFreBoughtItem(position: Int) {
        val itemAddsress = addressArrayList[position]
        getLatLngFromPrediction(itemAddsress)
    }

}

interface BottomSheetListener {
    fun onBottomSheetDataReceived(data: String, latLng: LatLng)
}