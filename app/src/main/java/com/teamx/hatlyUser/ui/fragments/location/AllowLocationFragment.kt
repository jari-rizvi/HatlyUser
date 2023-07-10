package com.teamx.hatlyUser.ui.fragments.location

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentAllowLocationBinding
import com.teamx.hatlyUser.ui.fragments.auth.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllowLocationFragment : BaseFragment<FragmentAllowLocationBinding, LoginViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_allow_location
    override val viewModel: Class<LoginViewModel>
        get() = LoginViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    @RequiresApi(Build.VERSION_CODES.N)
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
            findNavController().navigate(R.id.action_allowLocationocationFragment_to_homeFragment)
        }

        mViewDataBinding.txtLogin.setOnClickListener {
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
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


    @RequiresApi(Build.VERSION_CODES.N)

    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                findNavController().navigate(R.id.action_allowLocationocationFragment_to_homeFragment)
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                findNavController().navigate(R.id.action_allowLocationocationFragment_to_homeFragment)
            }
            else -> {
                Log.d("allowLocation", "locationPermissionRequest: not working")
            }
        }
    }

//    private val locationPermissionRequest =
//        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                when {
//                    permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
//                        // Precise location access granted.
//                        Log.d("allowLocation", "locationPermissionRequest: ACCESS_FINE_LOCATION")
//                        findNavController().navigate(R.id.action_allowLocationocationFragment_to_homeFragment)
//                    }
//                    permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
//                        // Only approximate location access granted.
//                        Log.d("allowLocation", "locationPermissionRequest: ACCESS_COARSE_LOCATION")
//                        findNavController().navigate(R.id.action_allowLocationocationFragment_to_homeFragment)
//                    }
//                    else -> {
//                        // No location access granted.
//                        Log.d("allowLocation", "locationPermissionRequest: else")
//                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                        val uri = Uri.fromParts("package", context?.packageName, null)
//                        intent.data = uri
//                        startActivity(intent)
//                    }
//                }
//            } else {
//                Log.d("allowLocation", "locationPermissionRequest: not working")
//            }
//        }

}