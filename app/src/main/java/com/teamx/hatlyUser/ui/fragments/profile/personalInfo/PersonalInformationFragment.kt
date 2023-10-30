package com.teamx.hatlyUser.ui.fragments.profile.personalInfo

import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentLoginBinding
import com.teamx.hatlyUser.databinding.FragmentPersonalInformationBinding
import com.teamx.hatlyUser.databinding.FragmentProfileManagementBinding
import com.teamx.hatlyUser.ui.fragments.auth.login.LoginViewModel
import com.teamx.hatlyUser.ui.fragments.profile.userprofile.ProfileManagementViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream


@AndroidEntryPoint
class PersonalInformationFragment : BaseFragment<FragmentPersonalInformationBinding, PersonalInformationViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_personal_information
    override val viewModel: Class<PersonalInformationViewModel>
        get() = PersonalInformationViewModel::class.java
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

        sharedViewModel.userData.observe(requireActivity()){
            mViewDataBinding.editText.setText(it.name)
            mViewDataBinding.editText3.text = it.email
            mViewDataBinding.editText2.text = it.contact ?: "Not Register"
        }

        mViewDataBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        mViewDataBinding.textView35.setOnClickListener {
            findNavController().navigate(R.id.action_personalInfoFragment_to_changePasswordFragment)
        }


    }


}