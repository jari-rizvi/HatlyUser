package com.teamx.hatlyUser.ui.fragments.hatlymart.stores.bottomsheet

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teamx.hatlyUser.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetRatingDeliveryFragment : BottomSheetDialogFragment() {


    private var bottomSheetRatDelListener: BottomSheetRatDelListener? = null

    var isDelivery = true

    private var finalValue = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet_rat_del, container, false)
    }

    var selectedValue = 0

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val txtLogin = view.findViewById<TextView>(R.id.txtLogin)
        val txtClearAll = view.findViewById<TextView>(R.id.txtClearAll)
        val txtTitle = view.findViewById<TextView>(R.id.txtTitle)
        val radio10 = view.findViewById<RadioButton>(R.id.radio10)
        val radio20 = view.findViewById<RadioButton>(R.id.radio20)
        val radio30 = view.findViewById<RadioButton>(R.id.radio30)
        val radio40 = view.findViewById<RadioButton>(R.id.radio40)
        val radio50 = view.findViewById<RadioButton>(R.id.radio50)
        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)

        val bundle = arguments
        if (bundle != null) {
            isDelivery = bundle.getBoolean("isDelivery", true)
            selectedValue = bundle.getInt("selectedValue", 0)


            if (isDelivery) {
                txtTitle.text = "Delivery Distance"

                radio10.text = "Under 10 mins"
                radio20.text = "Under 20 mins"
                radio30.text = "Under 30 mins"
                radio40.text = "Under 40 mins"
                radio50.text = "Under 50 mins"
            } else {
                txtTitle.text = "Ratings"

                radio50.text = "Rating 5.0 +"
                radio40.text = "Rating 4.0 +"
                radio30.text = "Rating 3.0 +"
                radio20.text = "Rating 2.0 +"
                radio10.text = "Rating 1.0 +"
            }

            radio10.isChecked = false
            radio20.isChecked = false
            radio30.isChecked = false
            radio40.isChecked = false
            radio50.isChecked = false

            if (selectedValue != 0) {

                when (selectedValue) {
                    10,1 -> {
                        radio10.isChecked = true
                    }
                    20,2 -> {
                        radio20.isChecked = true
                    }
                    30,3 -> {
                        radio30.isChecked = true
                    }
                    40,4 -> {
                        radio40.isChecked = true
                    }
                    50,5 -> {
                        radio50.isChecked = true
                    }
                }
            }
        }




        radioGroup.setOnCheckedChangeListener { group, checkedId ->

            val selectedRadioButton =
                view.findViewById<RadioButton>(checkedId) ?: return@setOnCheckedChangeListener

            // RadioButton ki value nikalen
            val selectedValue: String = selectedRadioButton.text.toString()

            finalValue = when (selectedValue) {
                "Under 10 mins" -> 10
                "Under 20 mins" -> 20
                "Under 30 mins" -> 30
                "Under 40 mins" -> 40
                "Under 50 mins" -> 50
                "Rating 5.0 +" -> 5
                "Rating 4.0 +" -> 4
                "Rating 3.0 +" -> 3
                "Rating 2.0 +" -> 2
                "Rating 1.0 +" -> 1
                else -> 0
            }

        }

        txtLogin.setOnClickListener {
//            radioGroup.clearCheck()
            bottomSheetRatDelListener?.onBottomSheetratDel(finalValue)
            dismiss()
        }

        txtClearAll.setOnClickListener {
            radioGroup.clearCheck()
            bottomSheetRatDelListener?.onBottomSheetratDel(null)
            dismiss()
        }


    }

    fun setBottomSheetListener(listener: BottomSheetRatDelListener) {
        bottomSheetRatDelListener = listener
    }

}

interface BottomSheetRatDelListener {
    fun onBottomSheetratDel(value: Int?)
}