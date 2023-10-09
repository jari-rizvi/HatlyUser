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
import android.widget.EditText
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
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
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentAllowLocationBinding
import com.teamx.hatlyUser.databinding.FragmentMapBinding
import com.teamx.hatlyUser.ui.fragments.auth.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.Locale

@AndroidEntryPoint
class BottomSheetAddressFragment : BottomSheetDialogFragment() {


    private lateinit var placesClient: PlacesClient
    private lateinit var addressEditText: EditText
    private lateinit var addressResultTextView: TextView

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

        Places.initialize(requireActivity(), "AIzaSyC73ppwAuW9cSty_PCi8t0zdbLJ3p3hBBY")
        placesClient = Places.createClient(requireActivity())

        addressEditText = view.findViewById(R.id.inpSearch)
        addressResultTextView = view.findViewById(R.id.addressResultTextView)

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

        addressResultTextView.setOnClickListener {
            val text = addressResultTextView.text.toString().trim()
            bottomSheetListener?.onBottomSheetDataReceived(text)
        }


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
                addressResultTextView.text = "Error: ${exception.message}"
            }
    }

    private fun handleAutocompletePredictions(predictions: List<AutocompletePrediction>) {
        val suggestions = predictions.map { prediction ->
            prediction.getFullText(null).toString()
        }

        Log.e("requestLocation", "getFullText, ${predictions[0].getFullText(null)}")
        Log.e("requestLocation", "getPrimaryText, ${predictions[0].getPrimaryText(null)}")
        Log.e("requestLocation", "getSecondaryText, ${predictions[0].getSecondaryText(null)}")



        // Display suggestions in your UI or use them as needed
//        addressResultTextView.text = suggestions.joinToString("\n")
    }

    fun setBottomSheetListener(listener: BottomSheetListener) {
        bottomSheetListener = listener
    }

}

interface BottomSheetListener {
    fun onBottomSheetDataReceived(data: String)
}