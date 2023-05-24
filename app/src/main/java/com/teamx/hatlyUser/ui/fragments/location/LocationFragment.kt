package com.teamx.hatlyUser.ui.fragments.location

import android.os.Bundle
import android.view.View
import androidx.navigation.NavBackStackEntry
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
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
        get() = com.teamx.hatlyUser.R.layout.fragment_location
    override val viewModel: Class<LoginViewModel>
        get() = LoginViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        options = navOptions {
            anim {
                enter = com.teamx.hatlyUser.R.anim.enter_from_left
                exit = com.teamx.hatlyUser.R.anim.exit_to_left
                popEnter = com.teamx.hatlyUser.R.anim.nav_default_pop_enter_anim
                popExit = com.teamx.hatlyUser.R.anim.nav_default_pop_exit_anim
            }
        }

        mViewDataBinding.imgBack.setOnClickListener {

            // Get the NavController from the NavHostFragment

            findNavController().popBackStack()
        }
//        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                // Back is pressed... Finishing the activity
////                activity?.finish()
//                popUpStack()
//            }
//        })
    }

    private fun performCustomBackStack() {
        val navController = Navigation.findNavController(requireActivity(), com.teamx.hatlyUser.R.id.nav_host_fragment)
        val homeBackStackEntry: NavBackStackEntry = navController.getBackStackEntry(com.teamx.hatlyUser.R.id.locationFragment)
        val loginFragmentDestinationId: Int = com.teamx.hatlyUser.R.id.loginFragment

        // Check if the LoginFragment is in the back stack
        if (homeBackStackEntry != null && homeBackStackEntry.destination.id == loginFragmentDestinationId) {
            // Pop the LoginFragment from the back stack
            navController.popBackStack(loginFragmentDestinationId, true)
        } else {
            // Perform the default back stack operation
            navController.navigateUp()
        }
    }


}