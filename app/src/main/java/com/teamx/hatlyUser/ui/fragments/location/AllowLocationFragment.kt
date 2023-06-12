package com.teamx.hatlyUser.ui.fragments.location

import android.os.Bundle
import android.view.View
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



//        requireActivity().onBackPressedDispatcher.addCallback(
//            requireActivity(),
//            object : OnBackPressedCallback(true) {
//                override fun handleOnBackPressed() {
//                    // Back is pressed... Finishing the activity
////                activity?.finish()
//                    Log.d("handleOnBackPressed", "handleOnBackPressed: back")
////                    requireActivity().finish()
//                }
//            })
    }

}