package com.teamx.hatlyUser.ui.fragments.location

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentLocationBinding
import com.teamx.hatlyUser.ui.fragments.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LocationFragment : BaseFragment<FragmentLocationBinding, LoginViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_location
    override val viewModel: Class<LoginViewModel>
        get() = LoginViewModel::class.java
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
            requireActivity().finish()
        }

        mViewDataBinding.txtEnterLocaion.setOnClickListener {
            it.findNavController().navigate(R.id.action_locationFragment_to_mapFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Back is pressed... Finishing the activity
//                activity?.finish()
                requireActivity().finish()
            }
        })
    }

}