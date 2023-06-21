package com.teamx.hatlyUser.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentHomeBinding
import com.teamx.hatlyUser.ui.activity.mainActivity.MainActivity
import com.teamx.hatlyUser.ui.fragments.auth.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, LoginViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_home
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

        mViewDataBinding.imgMenu.setOnClickListener {
            val activity = requireActivity() as MainActivity
            activity.openDrawer()
        }

        mViewDataBinding.textView9.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("parcel", true)
            findNavController().navigate(R.id.action_homeFragment_to_hatlyMartFragment, bundle)
        }

        mViewDataBinding.textView8.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_foodsHomeFragment)
        }

        mViewDataBinding.textView10.setOnClickListener {
//            findNavController().navigate(R.id.action_homeFragment_to_storesFragment)
            val bundle = Bundle()
            bundle.putBoolean("parcel", false)
            findNavController().navigate(R.id.action_homeFragment_to_storesFragment, bundle)
        }

        mViewDataBinding.textView11.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("parcel", false)
            findNavController().navigate(R.id.action_homeFragment_to_storesFragment, bundle)
        }

        mViewDataBinding.imgNotification.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_notificationFragment)
        }

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("handleOnBackPressed", "handleOnBackPressed: back")
                requireActivity().finish()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )


    }


}