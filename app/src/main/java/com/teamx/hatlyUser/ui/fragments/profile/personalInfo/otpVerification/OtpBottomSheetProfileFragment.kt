package com.teamx.hatlyUser.ui.fragments.profile.personalInfo.otpVerification

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.chaos.view.PinView
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.ui.fragments.location.map.bottomSheetAddSearch.BottomSheetListener
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint

class OtpBottomSheetProfileFragment(val profileOtpInterface: ProfileOtpInterface) :
    BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_otp, container, false)
    }

    var pinView = ""

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.hatlyIcon).visibility = View.GONE
        view.findViewById<ImageView>(R.id.imgBack).visibility = View.GONE
        view.findViewById<TextView>(R.id.textView23).visibility = View.GONE
        view.findViewById<TextView>(R.id.textView24).visibility = View.GONE
        val mainLayout = view.findViewById<ConstraintLayout>(R.id.mainLayout)
        val txtVerify = view.findViewById<TextView>(R.id.txtVerify)


        txtVerify.setOnClickListener {
            pinView = view.findViewById<PinView>(R.id.pinView).text.toString()
            if (pinView.isNotEmpty()) {
                profileOtpInterface.setPinView(pinView)
                dismiss()
            }else{
                if (isAdded) {
                    mainLayout.snackbar(getString(R.string.enter_otp))
                }
            }
        }

    }

}

interface ProfileOtpInterface {
    fun setPinView(pinView: String)
}

