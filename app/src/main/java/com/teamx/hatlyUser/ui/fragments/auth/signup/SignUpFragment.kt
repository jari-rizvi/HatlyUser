package com.teamx.hatlyUser.ui.fragments.auth.signup

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.gson.JsonObject
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentSignupBinding
import com.teamx.hatlyUser.utils.DialogHelperClass
import com.teamx.hatlyUser.utils.LocationPermission
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException


@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignupBinding, SignUpViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_signup
    override val viewModel: Class<SignUpViewModel>
        get() = SignUpViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

//    private var userEmail: String? = null
    private var password: String? = null
    private var name: String? = null
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
            findNavController().popBackStack()
        }

        mViewDataBinding.textView5.setOnClickListener {
            findNavController().popBackStack()
        }

        mViewDataBinding.txtLogin.setOnClickListener {
            if (isValidate()) {
                initialization()
                Log.d("createParams", "onViewCreated: ${createParams()}")
                mViewModel.signup(createParams())
            }
        }

        if (!mViewModel.signupResponse.hasActiveObservers()){
            mViewModel.signupResponse.observe(requireActivity(), Observer {
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
//                            if (data.role == "user"){
                                val bundle = Bundle()
                                bundle.putString("phone",data.contact)
                                bundle.putBoolean("fromSignup",true)
                                findNavController().navigate(R.id.action_signUpFragment_to_otpFragment,bundle)
//                            }
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

    private fun initialization() {
        name = mViewDataBinding.userFullName.text.toString().trim()
//        userEmail = mViewDataBinding.userEmail.text.toString().trim()
        userNumber = mViewDataBinding.userMobile.text.toString().trim()
        password = mViewDataBinding.userPassword.text.toString().trim()
    }

    private fun createParams() : JsonObject{
        val params = JsonObject()
        try {
            params.addProperty("name", name.toString())
//            params.addProperty("email", userEmail.toString())
            params.addProperty("contact", userNumber.toString())
            params.addProperty("password", password.toString())

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return params
    }

    private fun isValidate(): Boolean {

        if (mViewDataBinding.userFullName.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar("Enter name")
            return false
        }
//        if (mViewDataBinding.userEmail.text.toString().trim().isEmpty()) {
//            mViewDataBinding.root.snackbar("Enter email")
//            return false
//        }
//        if (!Patterns.EMAIL_ADDRESS.matcher(mViewDataBinding.userEmail.text.toString().trim())
//                .matches()
//        ) {
//            mViewDataBinding.root.snackbar("Invalid email")
//            return false
//        }
        if (mViewDataBinding.userMobile.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar("Enter mobile")
            return false
        }
        if (!Patterns.PHONE.matcher(mViewDataBinding.userMobile.text.toString().trim()).matches()) {
            mViewDataBinding.root.snackbar("Invalid phone")
            return false
        }
        if (mViewDataBinding.userPassword.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar("Enter password")
            return false
        }
        return true
    }


}