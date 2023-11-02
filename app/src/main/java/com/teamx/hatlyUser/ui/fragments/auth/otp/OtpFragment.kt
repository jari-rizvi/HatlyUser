package com.teamx.hatlyUser.ui.fragments.auth.otp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.gson.JsonObject
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.constants.NetworkCallPointsNest
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentOtpBinding
import com.teamx.hatlyUser.utils.LocationPermission
import com.teamx.hatlyUser.utils.PrefHelper
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import org.json.JSONException

@AndroidEntryPoint
class OtpFragment : BaseFragment<FragmentOtpBinding, OtpViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_otp
    override val viewModel: Class<OtpViewModel>
        get() = OtpViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private var pinView: String? = null
    private var phone: String? = ""
    private var fromSignup = false

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

        val bundle = arguments
        phone = bundle?.getString("phone")
        fromSignup = bundle?.getBoolean("fromSignup", false)!!

        mViewDataBinding.txtVerify.setOnClickListener {
            if (isValidate()) {
                initialization()
                if (fromSignup) {
                    Log.d("verifySignupO", "fromSignup: true")
                    mViewModel.verifySignupOtp(createSignUpVerifyParams())
                } else {
                    Log.d("verifySignupO", "fromSignup: false")
                    mViewModel.forgotPassVerifyOtp(createSignUpVerifyParams())
                }
            }
        }

        if (!mViewModel.verifySignupOtpResponse.hasActiveObservers()) {
            mViewModel.verifySignupOtpResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            Log.d("verifySignupO", "onViewCreated: $data")
                            CoroutineScope(Dispatchers.Main).launch {
                                data.token.let { it1 ->
                                    dataStoreProvider.saveUserToken(it1)
                                    NetworkCallPointsNest.TOKENER = it1
                                }
                            }
                            PrefHelper.getInstance(requireActivity()).setUserData(data)
                            if (LocationPermission.requestPermission(requireContext())) {
                                val userData =
                                    PrefHelper.getInstance(requireActivity()).getUserData()
                                sharedViewModel.setlocationmodel(userData?.location)
                                findNavController().navigate(R.id.action_otpFragment_to_mapFragment)
                            } else {
                                findNavController().navigate(R.id.action_otpFragment_to_allowLocationFragment)
                            }
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        if (isAdded) {

                        mViewDataBinding.root.snackbar(it.message!!)
                        }
                        Log.d("verifySignupO", "onViewCreated: ${it.message!!}")
                    }
                }
            }
        }

        if (!mViewModel.forgotPassVerifyOtpResponse.hasActiveObservers()) {
            mViewModel.forgotPassVerifyOtpResponse.observe(requireActivity(), Observer {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->

                            val bundle = Bundle()
                            bundle.putString("uniqueId", data.uniqueId)
                            bundle.putString("phone", phone)
//                            if (data.verified) {
                            findNavController().navigate(
                                R.id.action_otpFragment_to_createPasswordFragment,
                                bundle
                            )
//                                if (fromSignup) {
//                                } else {
//                                    val bundle1 = Bundle()
//                                    bundle1.putString("phone", phone)
//                                    findNavController().navigate(
//                                        R.id.action_otpFragment_to_createPasswordFragment,
//                                        bundle1
//                                    )
//                                }
//                            }
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                    }
                }
            })
        }

        mViewDataBinding.textView24.isEnabled = false
        timerStart()
        mViewDataBinding.textView24.setOnClickListener {
            mViewModel.resendOtp(createVerifyForgotPassParams())
        }

        if (!mViewModel.resendOtpResponse.hasActiveObservers()) {
            mViewModel.resendOtpResponse.observe(requireActivity(), Observer {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            if (data.success) {
                                mViewDataBinding.root.snackbar("Otp resend")
                            }
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                    }
                }
            })
        }

    }

    private fun initialization() {
        pinView = mViewDataBinding.pinView.text.toString()
    }

    private fun createSignUpVerifyParams(): JsonObject {
        val params = JsonObject()
        try {
            params.addProperty("contact", phone)
            params.addProperty("verificationCode", pinView)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return params
    }

    private fun createVerifyForgotPassParams(): JsonObject {
        val params = JsonObject()
        try {
            params.addProperty("contact", phone)
            params.addProperty("verificationCode", pinView)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return params
    }

    private fun isValidate(): Boolean {
        if (mViewDataBinding.pinView.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar("Enter Otp")
            return false
        }
        return true
    }

    private fun timerStart() {
        val durationSeconds = 30
        var remainingSeconds = durationSeconds

        lifecycleScope.launch {
            while (remainingSeconds > 0) {
                println("Remaining time: $remainingSeconds seconds")
                Log.d("timerStart", "working $remainingSeconds")
                mViewDataBinding.textView23.text = "Resend OTP in $remainingSeconds sec"
                delay(1000) // Delay for 1 second
                remainingSeconds--
            }

            println("Time's up!")
            Log.d("timerStart", "Time's up!")
            mViewDataBinding.textView24.isEnabled = true
        }
    }

}