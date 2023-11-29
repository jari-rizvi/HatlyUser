package com.teamx.hatlyUser.ui.fragments.auth.forgotpassword

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.gson.JsonObject
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentForgotPasswordBinding
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException

@AndroidEntryPoint
class ForgotPasswordFragment :
    BaseFragment<FragmentForgotPasswordBinding, ForgotPasswordViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_forgot_password
    override val viewModel: Class<ForgotPasswordViewModel>
        get() = ForgotPasswordViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private var userNumber: String? = null

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
            it.findNavController().popBackStack()
        }

        mViewDataBinding.txtLogin.setOnClickListener {
//            findNavController().navigate(R.id.action_forgotPasswordFragment_to_otpFragment)
            if (isValidate()) {
                initialization()
                Log.d("createParams", "onViewCreated: ${createParams()}")
                mViewModel.forgotPass(createParams())
            }
        }

        if (!mViewModel.forgotPassResponse.hasActiveObservers()) {
            mViewModel.forgotPassResponse.observe(requireActivity(), Observer {
                when (it.status) {
                    Resource.Status.AUTH -> {
                        loadingDialog.dismiss()
                        onToSignUpPage()
                    }
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }
                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            if (data.success) {
                                val bundle = Bundle()
                                bundle.putString("phone",userNumber)
                                bundle.putBoolean("fromSignup",false)
                                findNavController().navigate(R.id.action_forgotPasswordFragment_to_otpFragment,bundle)
                            }
                        }
                    }
                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        Log.d("createParams", "Resource.Status.ERROR: ${it.message}")
                    }
                }
            })
        }

    }

    private fun createParams() : JsonObject {
        val params = JsonObject()
        try {
            params.addProperty("contact", userNumber.toString())
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return params
    }

    private fun initialization() {
        userNumber = mViewDataBinding.userPhone.text.toString().trim()
    }

    private fun isValidate(): Boolean {

        if (mViewDataBinding.userPhone.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar("Enter phone")
            return false
        }
        return true
    }



}