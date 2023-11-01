package com.teamx.hatlyUser.ui.fragments.special.parceldetail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentParcelDetailBinding
import com.teamx.hatlyUser.ui.fragments.special.sendparcel.SendParcelViewModel
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ParcelDetailFragment : BaseFragment<FragmentParcelDetailBinding, ParcelDetailViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_parcel_detail
    override val viewModel: Class<ParcelDetailViewModel>
        get() = ParcelDetailViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    var weightParcel = ""
    var lengthParcel = ""
    var widthParcel = ""
    var heightParcel = ""

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

        // Create an ArrayAdapter using the string array and a default spinner layout
//        val adapter = ArrayAdapter.createFromResource(
//            requireContext(),
//            R.array.spinner1,
//            android.R.layout.simple_spinner_item
//        )
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//
//
//        mViewDataBinding.spinner1.adapter = adapter

        setSpinnerAdapter(R.array.weight, mViewDataBinding.spinner1)
        setSpinnerAdapter(R.array.size, mViewDataBinding.spinner2)
        setSpinnerAdapter(R.array.size, mViewDataBinding.spinner3)
        setSpinnerAdapter(R.array.size, mViewDataBinding.spinner4)

        mViewDataBinding.spinner1.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> {
                            weightParcel = ""
                        }

                        1 -> {
                            weightParcel = WeightUnit.KilloGram.value
                        }

                        2 -> {
                            weightParcel = WeightUnit.Gram.value
                        }

                        3 -> {
                            weightParcel = WeightUnit.Pound.value
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

        mViewDataBinding.spinner2.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> {
                            lengthParcel = ""
                        }

                        1 -> {
                            lengthParcel = LengthHeightUnit.METERS.value
                        }

                        2 -> {
                            lengthParcel = LengthHeightUnit.FEET.value
                        }

                        3 -> {
                            lengthParcel = LengthHeightUnit.INCHES.value
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        mViewDataBinding.spinner3.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> {
                            widthParcel = ""
                        }

                        1 -> {
                            widthParcel = LengthHeightUnit.METERS.value
                        }

                        2 -> {
                            widthParcel = LengthHeightUnit.FEET.value
                        }

                        3 -> {
                            widthParcel = LengthHeightUnit.INCHES.value
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        mViewDataBinding.spinner4.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> {
                            heightParcel = ""
                        }

                        1 -> {
                            heightParcel = LengthHeightUnit.METERS.value
                        }

                        2 -> {
                            heightParcel = LengthHeightUnit.FEET.value
                        }

                        3 -> {
                            heightParcel = LengthHeightUnit.INCHES.value
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }


        mViewDataBinding.imgIncreament.setOnClickListener {
            quantityCal(parcelQuantity + 1)
        }

        mViewDataBinding.imgDecreament.setOnClickListener {
            quantityCal(parcelQuantity - 1)
        }

        mViewDataBinding.txtLogin.setOnClickListener {
            val inpParcelWeight = mViewDataBinding.inpParcelWeight.text.toString()
            val inpParcelLength = mViewDataBinding.inpParcelLength.text.toString()
            val inpParcelWidth = mViewDataBinding.inpParcelWidth.text.toString()
            val inpParcelHeight = mViewDataBinding.inpParcelHeight.text.toString()
            val inpBrief = mViewDataBinding.inpBrief.text.toString()


            if (sendNextPage()) {
//                Log.d("spinner1", "weightParcel: $weightParcel")
//                Log.d("spinner1", "widthParcel: $lengthParcel")
//                Log.d("spinner1", "widthParcel: $widthParcel")
//                Log.d("spinner1", "heightParcel: $heightParcel")

                val bundle = Bundle()
                bundle.putString("inpWhatSending", mViewDataBinding.inpWhatSending.text.toString())
                bundle.putInt("parcelQuantity", parcelQuantity)
                bundle.putString("inpBrief", inpBrief)

                bundle.putString("inpParcelWeight", inpParcelWeight)
                bundle.putString("inpParcelLength", inpParcelLength)
                bundle.putString("inpParcelWidth", inpParcelWidth)
                bundle.putString("inpParcelHeight", inpParcelHeight)

                bundle.putString("weightParcel", weightParcel)
                bundle.putString("lengthParcel", lengthParcel)
                bundle.putString("widthParcel", widthParcel)
                bundle.putString("heightParcel", heightParcel)
                findNavController().navigate(
                    R.id.action_parcelDetailFragment_to_parcelLocationFragment,
                    bundle
                )
            }
        }


        mViewDataBinding.inpQuantity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                parcelQuantity = if (s!!.isNotEmpty()) {
                    s.toString().toInt()
                } else {
                    0
                }
            }

        })
    }


    private fun sendNextPage(): Boolean {
        val inpWhatSending = mViewDataBinding.inpWhatSending.text.toString()

        if (inpWhatSending.isEmpty() || inpWhatSending.isBlank()) {
            mViewDataBinding.root.snackbar("What are you sending?")
            return false
        }

        if (parcelQuantity == 0) {
            mViewDataBinding.root.snackbar("Enter Quantity")
            return false
        }
        return true
    }

    private fun setSpinnerAdapter(spinnerArray: Int, spinner: Spinner) {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            spinnerArray,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    var parcelQuantity = 0

    private fun quantityCal(qnt: Int) {
        if (qnt < 1) {
            return
        }
        parcelQuantity = qnt
        mViewDataBinding.inpQuantity.setText(parcelQuantity.toString())
    }


}


enum class WeightUnit(val value: String) {
    KilloGram("killo gram"),
    Gram("gram"),
    Pound("pound")
}

enum class LengthHeightUnit(val value: String) {
    METERS("meter"),
    FEET("feet"),
    INCHES("inch")
}