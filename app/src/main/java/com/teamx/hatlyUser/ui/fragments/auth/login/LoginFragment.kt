package com.teamx.hatlyUser.ui.fragments.auth.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.gson.JsonObject
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentLoginBinding
import com.teamx.hatlyUser.utils.PrefHelper
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import kotlin.random.Random


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_login
    override val viewModel: Class<LoginViewModel>
        get() = LoginViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private var userEmail: String? = null
    private var userPass: String? = null
    private var randNum: String? = null

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

        PrefHelper.getInstance(requireActivity()).setNotFirstTime(true)

        randNum = generateRandom().toString()

        mViewDataBinding.textView4.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        mViewDataBinding.textView5.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        mViewDataBinding.txtLogin.setOnClickListener {
            if (isValidate()) {
                initialization()
                mViewModel.login(createParams())
            }
        }

        if (!mViewModel.loginResponse.hasActiveObservers()) {
            mViewModel.loginResponse.observe(requireActivity(), Observer {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }
                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            if (data.verified) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    dataStoreProvider.saveUserToken(data.token)
//                                    dataStoreProvider.saveDeviceData(randNum!!)
                                    dataStoreProvider.saveDeviceData("88765275963748185512")
                                    Log.d("allStoresResponse", "login: ${data.deviceData}")
                                    Log.d("allStoresResponse", "randNum: ${randNum}")
                                }
                                findNavController().navigate(R.id.action_loginFragment_to_locationFragment)
                            }
                        }
                    }
                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        mViewDataBinding.root.snackbar(it.message!!)
                    }
                }
            })
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

    private fun initialization() {
        userEmail = mViewDataBinding.userEmail.text.toString().trim()
        userPass = mViewDataBinding.userPassword.text.toString().trim()
    }

    private fun createParams(): JsonObject {
        val params = JsonObject()
        try {
            params.addProperty("contact", userEmail.toString())
            params.addProperty("password", userPass.toString())
//            params.addProperty("deviceData", randNum)
            params.addProperty("deviceData", "88765275963748185512")

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return params
    }

    private fun isValidate(): Boolean {

        if (mViewDataBinding.userEmail.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar("Enter email or phone")
            return false
        }
        if (mViewDataBinding.userPassword.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar("Enter password")
            return false
        }
        return true
    }

    fun generateRandom(): Long {
        val rangeStart = 1000000000000000000L
        val rangeEnd = 9000000000000000000L

        return Random.nextLong(rangeStart, rangeEnd)
    }

}