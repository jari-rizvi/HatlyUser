package com.teamx.hatlyUser.ui.fragments.location.map.bottomSheetAddressDetail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatCheckedTextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teamx.hatlyUser.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetAddressDetailFragment : BottomSheetDialogFragment() {

    private lateinit var imgLabelHome: AppCompatCheckedTextView
    private lateinit var imgLabelWork: AppCompatCheckedTextView
    private lateinit var imgLabelOther: AppCompatCheckedTextView
    private lateinit var inpOtherLabel: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_detail, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgLabelHome = view.findViewById(R.id.imgLabelHome)
        imgLabelWork = view.findViewById(R.id.imgLabelWork)
        imgLabelOther = view.findViewById(R.id.imgLabelOther)
        inpOtherLabel = view.findViewById(R.id.inpOtherLabel)

        imgLabelHome.setOnClickListener {
            imgLabelHome.isChecked = true
            imgLabelWork.isChecked = false
            imgLabelOther.isChecked = false
            inpOtherLabel.visibility = View.GONE
        }

        imgLabelWork.setOnClickListener {
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

    }


}
