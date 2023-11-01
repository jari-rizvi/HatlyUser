package com.teamx.hatlyUser.ui.fragments.special.ParcelLocation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.navigation.navOptions
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentParcelDetailBinding
import com.teamx.hatlyUser.databinding.FragmentParcelLocationBinding
import com.teamx.hatlyUser.ui.fragments.special.parceldetail.ParcelDetailViewModel
import com.teamx.hatlyUser.ui.fragments.special.sendparcel.SendParcelViewModel
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ParcelLocationFragment : BaseFragment<FragmentParcelLocationBinding, ParcelLocationViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_parcel_location
    override val viewModel: Class<ParcelLocationViewModel>
        get() = ParcelLocationViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

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

        val bundle = arguments
        if (bundle != null){

            val inpWhatSending = bundle.getString("inpWhatSending","")
            val parcelQuantity = bundle.getInt("parcelQuantity",0)
            val inpBrief = bundle.getString("inpBrief","")
            val inpParcelWeight = bundle.getString("inpParcelWeight","")
            val inpParcelLength = bundle.getString("inpParcelLength","")
            val inpParcelWidth = bundle.getString("inpParcelWidth","")
            val inpParcelHeight = bundle.getString("inpParcelHeight","")
            val weightParcel = bundle.getString("weightParcel","")
            val lengthParcel = bundle.getString("lengthParcel","")
            val widthParcel = bundle.getString("widthParcel","")
            val heightParcel = bundle.getString("heightParcel","")

            Log.d("spinner1", "inpWhatSending: $inpWhatSending")
            Log.d("spinner1", "parcelQuantity: $parcelQuantity")
            Log.d("spinner1", "inpBrief: $inpBrief")
            Log.d("spinner1", "inpParcelWeight: $inpParcelWeight")
            Log.d("spinner1", "inpParcelLength: $inpParcelLength")
            Log.d("spinner1", "inpParcelWidth: $inpParcelWidth")
            Log.d("spinner1", "inpParcelHeight: $inpParcelHeight")
            Log.d("spinner1", "weightParcel: $weightParcel")
            Log.d("spinner1", "lengthParcel: $lengthParcel")
            Log.d("spinner1", "widthParcel: $widthParcel")
            Log.d("spinner1", "heightParcel: $heightParcel")
        }

    }

}