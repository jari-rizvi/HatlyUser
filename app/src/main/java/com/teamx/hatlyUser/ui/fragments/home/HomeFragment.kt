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
import com.teamx.hatlyUser.constants.NetworkCallPointsNest.Companion.MART
import com.teamx.hatlyUser.databinding.FragmentHomeBinding
import com.teamx.hatlyUser.ui.activity.mainActivity.MainActivity
import com.teamx.hatlyUser.ui.fragments.auth.login.LoginViewModel
import com.teamx.hatlyUser.utils.PrefHelper
import com.teamx.hatlyUser.utils.enum_.Marts
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


        val userData = PrefHelper.getInstance(requireActivity()).getUserData()

        userData?.let {
            Log.d("setUserData", "onViewCreated: ${it}")

            mViewDataBinding.textView20.text = try {
                it.location.address
            } catch (e: Exception) {
                "Select your location"
            }

            mViewDataBinding.textView41.text = try {
                it.location.label
            } catch (e: Exception) {
                "Current Location:"
            }
            sharedViewModel.setUserData(it)
        }

        mViewDataBinding.homeAddress.setOnClickListener {
            val locationModel = userData?.location
//            locationModel!!.isAction =  "Update"

                if (locationModel != null) {
                    locationModel.isAction = "Update"

                }

            sharedViewModel.setlocationmodel(locationModel)
            findNavController().navigate(R.id.action_homeFragment_to_mapFragment)
        }

        mViewDataBinding.imgMenu.setOnClickListener {
            val activity = requireActivity() as MainActivity
            activity.openDrawer()
        }

        mViewDataBinding.textView9.setOnClickListener {
            MART = Marts.HATLY_MART
            val bundle = Bundle()
            bundle.putBoolean("parcel", true)
            if (isAdded) {
                findNavController().navigate(R.id.action_homeFragment_to_hatlyMartFragment, bundle)
            }
        }

        mViewDataBinding.textView8.setOnClickListener {
            MART = Marts.FOOD
            findNavController().navigate(R.id.action_homeFragment_to_foodsHomeFragment)
        }

        mViewDataBinding.textView10.setOnClickListener {
            MART = Marts.GROCERY
//            findNavController().navigate(R.id.action_homeFragment_to_storesFragment)
            val bundle = Bundle()
            bundle.putBoolean("parcel", false)
            findNavController().navigate(R.id.action_homeFragment_to_storesFragment, bundle)
        }

        mViewDataBinding.textView11.setOnClickListener {
            MART = Marts.HEALTH_BEAUTY
            val bundle = Bundle()
            bundle.putBoolean("parcel", false)
            findNavController().navigate(R.id.action_homeFragment_to_storesFragment, bundle)
        }

        mViewDataBinding.textView112.setOnClickListener {
            MART = Marts.HOME_BUSINESS
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