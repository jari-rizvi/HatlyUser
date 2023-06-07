package com.teamx.hatlyUser.ui.fragments.signup

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentSignupBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignupBinding, SignUpViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_signup
    override val viewModel: Class<SignUpViewModel>
        get() = SignUpViewModel::class.java
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

        mViewDataBinding.textView5.setOnClickListener {
            findNavController().popBackStack()
        }

        mViewDataBinding.txtLogin.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_locationFragment)
        }

    }


}