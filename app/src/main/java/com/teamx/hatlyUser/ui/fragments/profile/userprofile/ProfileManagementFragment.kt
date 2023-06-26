package com.teamx.hatlyUser.ui.fragments.profile.userprofile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentLoginBinding
import com.teamx.hatlyUser.databinding.FragmentProfileManagementBinding
import com.teamx.hatlyUser.ui.fragments.auth.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileManagementFragment :
    BaseFragment<FragmentProfileManagementBinding, ProfileManagementViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_profile_management
    override val viewModel: Class<ProfileManagementViewModel>
        get() = ProfileManagementViewModel::class.java
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

        mViewDataBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("handleOnBackPressed", "handleOnBackPressed: back")
                findNavController().popBackStack(R.id.homeFragment,false)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )

        mViewDataBinding.userPersonal.setOnClickListener {
            findNavController().navigate(R.id.action_profileManagementFragment_to_personalInfoFragment)
        }

        mViewDataBinding.userLocation.setOnClickListener {
            findNavController().navigate(R.id.action_profileManagementFragment_to_locationFragment)
        }

        mViewDataBinding.userOtherHistory.setOnClickListener {
            findNavController().navigate(R.id.action_profileManagementFragment_to_orderHistoryFragment)
        }

    }


}