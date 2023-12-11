package com.teamx.hatlyUser.ui.fragments.special.ParcelLocation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.MainApplication
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentParcelLocationBinding
import com.teamx.hatlyUser.ui.fragments.auth.login.Model.Location
import com.teamx.hatlyUser.ui.fragments.hatlymart.stores.model.Coordinates
import com.teamx.hatlyUser.ui.fragments.special.ParcelLocation.createParcel.Details
import com.teamx.hatlyUser.ui.fragments.special.ParcelLocation.createParcel.ReceiverLocation
import com.teamx.hatlyUser.ui.fragments.special.ParcelLocation.createParcel.SenderLocation
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException

@AndroidEntryPoint
class ParcelLocationFragment :
    BaseFragment<FragmentParcelLocationBinding, ParcelLocationViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_parcel_location
    override val viewModel: Class<ParcelLocationViewModel>
        get() = ParcelLocationViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private var inpWhatSending = ""
    private var parcelQuantity = 0
    private var inpBrief = ""
    private var inpParcelWeight: Double? = null
    private var inpParcelLength: Double? = null
    private var inpParcelWidth: Double? = null
    var inpParcelHeight: Double? = null
    private var weightParcel = ""
    private var lengthParcel = ""
    private var widthParcel = ""
    private var heightParcel = ""

//    private var isFromSender = -1

    private var senderLocation: Location? = null
    private var reciverLocation: Location? = null

    private var reciverLatLng = LatLng(0.0, 0.0)
    private var senderLatLng = LatLng(0.0, 0.0)


//    private var isFrom = false

    private var senderAddress = ""
    private var reciverAddress = ""

    private var senderPhoneNum = ""
    private var reciverPhoneNum = ""

    var isFromSenderContact = false

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

        mViewDataBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        val bundle = arguments
        if (bundle != null) {

            inpWhatSending = bundle.getString("inpWhatSending", "")
            parcelQuantity = bundle.getInt("parcelQuantity", 0)
            inpBrief = bundle.getString("inpBrief", "")
            inpParcelWeight = bundle.getDouble("inpParcelWeight")
            inpParcelLength = bundle.getDouble("inpParcelLength")
            inpParcelWidth = bundle.getDouble("inpParcelWidth")
            inpParcelHeight = bundle.getDouble("inpParcelHeight")
            weightParcel = bundle.getString("weightParcel", "")
            lengthParcel = bundle.getString("lengthParcel", "")
            widthParcel = bundle.getString("widthParcel", "")
            heightParcel = bundle.getString("heightParcel", "")

            Log.d("inpWhatSending", "inpWhatSending: $inpWhatSending")
        }



        mViewDataBinding.inpPickAddress.setOnClickListener {
//            isFrom = true
            val bundle = Bundle()
            bundle.putString("fromParcel", "senderData")
            findNavController().navigate(
                R.id.action_parcelLocationFragment_to_locationFragment,
                bundle
            )
        }

        mViewDataBinding.inpDeliveryAddress.setOnClickListener {
//            isFrom = false
            val bundle = Bundle()
            bundle.putString("fromParcel", "reciverData")
            findNavController().navigate(
                R.id.action_parcelLocationFragment_to_locationFragment,
                bundle
            )
        }


        mViewDataBinding.userEmail656665.setOnClickListener {
            isFromSenderContact = true
            openPhoneBook()
        }

        mViewDataBinding.imgDeliveryPhone.setOnClickListener {
            isFromSenderContact = false
            openPhoneBook()
        }

        mViewModel.fareCalculationResponse.observe(requireActivity()) {
            when (it.status) {
                Resource.Status.AUTH -> {
                    loadingDialog.dismiss()
                    onToSignUpPage()
                }
                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }

                Resource.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    it.data?.let { data ->
                        mViewDataBinding.txtFarePrice.text = try {
                            "${data.fare} ${
                                MainApplication.context.getString(
                                R.string.aed)}"
                        } catch (e: Exception) {
                            ""
                        }

                    }
                }

                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    if (isAdded) {
                        mViewDataBinding.root.snackbar(it.message!!)
                    }
                }
            }
        }

        mViewDataBinding.txtLogin.setOnClickListener {
            mViewModel.createParcel(createJson())
        }

        mViewModel.createParcelResponse.observe(requireActivity()) {
            when (it.status) {
                Resource.Status.AUTH -> {
                    loadingDialog.dismiss()
                    onToSignUpPage()
                }
                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }

                Resource.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    it.data?.let { data ->
                        if (data.success) {
                            if (isAdded) {
                                findNavController().popBackStack(R.id.specialOrderFragment,false)
                            }
                        }
                    }
                }

                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    if (isAdded) {
                        mViewDataBinding.root.snackbar(it.message!!)
                    }
                }
            }
        }

        parcelObserver()
        mViewDataBinding.inpPickAddress.text = senderAddress
        mViewDataBinding.inpDeliveryAddress.text = reciverAddress
    }

    private fun sendNextPage(): Boolean {
        val inpPickAddress = mViewDataBinding.inpPickAddress.text.toString()
        val inpSenderPhone = mViewDataBinding.inpSenderPhone.text.toString()
        val inpDeliveryAddress = mViewDataBinding.inpDeliveryAddress.text.toString()
        val inpDeliveryPhone = mViewDataBinding.inpDeliveryPhone.text.toString()

        if (inpPickAddress.isEmpty() || inpPickAddress.isBlank()) {
            mViewDataBinding.root.snackbar("Select Sender Address")
            return false
        }

        if (inpSenderPhone.isEmpty() || inpSenderPhone.isBlank()) {
            mViewDataBinding.root.snackbar("Select Sender Phone Number")
            return false
        }

        if (inpDeliveryAddress.isEmpty() || inpDeliveryAddress.isBlank()) {
            mViewDataBinding.root.snackbar("Select Receiver Address")
            return false
        }

        if (inpDeliveryPhone.isEmpty() || inpDeliveryPhone.isBlank()) {
            mViewDataBinding.root.snackbar("Select Receiver Phone Number")
            return false
        }

        if (parcelQuantity == 0) {
            mViewDataBinding.root.snackbar("Enter Quantity")
            return false
        }
        return true
    }

    private fun openPhoneBook() {
        val pickContactIntent =
            Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
        contactPicker.launch(pickContactIntent)
    }

    private val contactPicker =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val contactUri = result.data?.data
                val phoneNumber = contactUri?.let { getPhoneNumberFromContactUri(it) }
                if (phoneNumber != null) {
                    // Use the retrieved phone number in your app.
                    if (isFromSenderContact) {
                        senderPhoneNum = phoneNumber
                        mViewDataBinding.inpSenderPhone.setText(phoneNumber)
                    } else {
                        reciverPhoneNum = phoneNumber
                        mViewDataBinding.inpDeliveryPhone.setText(phoneNumber)

                    }
                    Log.d("contactPickerLauncher", ": ${phoneNumber}")
                }
            }
        }

    private fun getPhoneNumberFromContactUri(contactUri: Uri): String? {
        val contentResolver = requireActivity().contentResolver
        val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)
        val cursor = contentResolver.query(contactUri, projection, null, null, null)

        cursor?.use {
            if (it.moveToFirst()) {
                val phoneNumberColumnIndex =
                    it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                return it.getString(phoneNumberColumnIndex)
            }
        }
        return null
    }

    private fun parcelObserver() {
        sharedViewModel.parcelLocation.observe(requireActivity()) { locationModel ->
            if (locationModel != null) {
                if (locationModel.isFromSender) {
                    senderLocation = locationModel
                    senderAddress = senderLocation!!.address

                    reciverLatLng = LatLng(senderLocation!!.lat, senderLocation!!.lng)
                } else {
                    reciverLocation = locationModel
                    reciverAddress = reciverLocation!!.address

                    senderLatLng = LatLng(reciverLocation!!.lat, reciverLocation!!.lng)
                }

                if ((reciverLatLng.latitude != 0.0 && reciverLatLng.longitude != 0.0) && (senderLatLng.latitude != 0.0 && senderLatLng.longitude != 0.0)) {
                    Log.d("fareCalculation", "onViewCreated: working")
                    mViewModel.fareCalculation(createFareJson(reciverLatLng, senderLatLng))
                }
            }
        }


    }

    private fun createFareJson(reciverLatLng: LatLng, senderLatLng: LatLng): JsonObject {
        val params = JsonObject()
        try {
            params.add(
                "receiverLocation",
                Gson().toJsonTree(Coordinates(reciverLatLng.latitude, reciverLatLng.longitude))
            )
            params.add(
                "senderLocation",
                Gson().toJsonTree(Coordinates(senderLatLng.latitude, senderLatLng.longitude))
            )
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return params
    }

    //    private var inpWhatSending = ""
//    private var parcelQuantity = 0
//    private var inpBrief = ""
//    private var inpParcelWeight = ""
//    private var inpParcelLength = ""
//    private var inpParcelWidth = ""
//    private var inpParcelHeight = ""
//    private var weightParcel = ""
//    private var lengthParcel = ""
//    private var widthParcel = ""
//    private var heightParcel = ""
    private fun createJson(): JsonObject {
        val params = JsonObject()
        try {

            val inpParcelHeight: Double? = if (inpParcelHeight == 0.0) null else inpParcelHeight
            val inpParcelLength: Double? = if (inpParcelLength == 0.0) null else inpParcelLength
            val inpParcelWeight: Double? = if (inpParcelWeight == 0.0) null else inpParcelWeight
            val inpParcelWidth: Double? = if (inpParcelWidth == 0.0) null else inpParcelWidth

            val inpBrief: String? = inpBrief.ifBlank { null }
            val heightParcel: String? = heightParcel.ifBlank { null }
            val lengthParcel: String? = lengthParcel.ifBlank { null }
            val weightParcel: String? = weightParcel.ifBlank { null }
            val widthParcel: String? = widthParcel.ifBlank { null }


            params.add(
                "details",
                Gson().toJsonTree(
                    Details(
                        description = inpBrief,
                        height = inpParcelHeight,
                        heightUnit = heightParcel,
                        item = inpWhatSending,
                        length = inpParcelLength,
                        lengthUnit = lengthParcel,
                        qty = parcelQuantity,
                        weight = inpParcelWeight,
                        weightUnit = weightParcel,
                        width = inpParcelWidth,
                        widthUnit = widthParcel
                    )
                )
            )


//            if (senderLocation != null && senderPhoneNum.isNotEmpty()) {
//                params.add(
//                    "senderLocation",
//                    Gson().toJsonTree(SenderLocation(senderLocation!!._id, senderPhoneNum))
//                )
//            }

            if (senderLocation!= null){
                params.add(
                    "senderLocation",
                    Gson().toJsonTree(SenderLocation(senderLocation!!._id, senderPhoneNum))
                )
            }


//            if (reciverLocation != null && reciverPhoneNum.isNotEmpty()) {
//                params.add(
//                    "receiverLocation",
//                    Gson().toJsonTree(ReceiverLocation(reciverLocation!!._id, reciverPhoneNum))
//                )
//            }

            if (reciverLocation!=null){
                params.add(
                    "receiverLocation",
                    Gson().toJsonTree(ReceiverLocation(reciverLocation!!._id, reciverPhoneNum))
                )
            }



//
//            params.add(
//                "senderLocation",
//                Gson().toJsonTree(Coordinates(senderLatLng.latitude, senderLatLng.longitude))
//            )
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return params
    }

    override fun onDestroy() {
        super.onDestroy()
        reciverLatLng = LatLng(0.0, 0.0)
        senderLatLng = LatLng(0.0, 0.0)
        senderAddress = ""
        reciverAddress = ""
//        if (sharedViewModel.parcelLocation.hasActiveObservers()) {
//            sharedViewModel.parcelLocation.removeObservers(viewLifecycleOwner)
//        }
        sharedViewModel.setParcelLocation(null)
    }


}

//enum class SelectAddressParcel {
//    Sender,
//    Reciver
//}