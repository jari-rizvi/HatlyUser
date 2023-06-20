package com.teamx.hatlyUser.ui.fragments.auth.otp

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import bolts.Task
import com.google.gson.JsonObject
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentOtpBinding
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
        fromSignup = bundle?.getBoolean("fromSignup")!!

        mViewDataBinding.txtVerify.setOnClickListener {
            if (isValidate()) {
                initialization()
                mViewModel.verifyOtp(createParams())
            }
        }

        if (!mViewModel.verifyOtpResponse.hasActiveObservers()) {
            mViewModel.verifyOtpResponse.observe(requireActivity(), Observer {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }
                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            if (data.verified) {
                                if (fromSignup) {
                                    findNavController().navigate(R.id.action_otpFragment_to_allowLocationFragment)
                                } else {
                                    val bundle1 = Bundle()
                                    bundle1.putString("phone", phone)
                                    findNavController().navigate(
                                        R.id.action_otpFragment_to_createPasswordFragment,
                                        bundle1
                                    )
                                }
                            }
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
            mViewModel.resendOtp(createResendParams())
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

    private fun createParams(): JsonObject {
        val params = JsonObject()
        try {
            params.addProperty("contact", phone)
            params.addProperty("verificationCode", pinView)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return params
    }

    private fun createResendParams(): JsonObject {
        val params = JsonObject()
        try {
            params.addProperty("contact", phone)
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

    fun timerStart() {
        val durationSeconds = 30
        var remainingSeconds = durationSeconds

        lifecycleScope.launch{
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

//        val timerJob = launch {
//
//        }

        // Wait for the timer to complete
//        timerJob.join()
    }

}