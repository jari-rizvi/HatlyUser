package com.teamx.hatlyUser.ui.fragments.forgotpassword

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentForgotPasswordBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding, ForgotPasswordViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_forgot_password
    override val viewModel: Class<ForgotPasswordViewModel>
        get() = ForgotPasswordViewModel::class.java
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

        mViewDataBinding.txtLogin.setOnClickListener {
            findNavController().navigate(R.id.action_forgot_to_otp)
        }


    }


}