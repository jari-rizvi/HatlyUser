package com.teamx.hatlyUser.ui.fragments.auth.createpassword

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
import com.teamx.hatlyUser.databinding.FragmentCreatePasswordBinding
import com.teamx.hatlyUser.utils.LocationPermission
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException


@AndroidEntryPoint
class CreatePasswordFragment :
    BaseFragment<FragmentCreatePasswordBinding, CreatePasswordViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_create_password
    override val viewModel: Class<CreatePasswordViewModel>
        get() = CreatePasswordViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private var userNew: String? = null
    private var userConfirmPass: String? = null

    private var phone: String? = ""
    private var uniqueId: String? = ""

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
            if (isValidate()) {
                initialization()
                mViewModel.updatePass(createParams())
            }
        }

        val bundle = arguments
        phone = bundle?.getString("phone")
        uniqueId = bundle?.getString("uniqueId")
        Log.d("createParams", "onViewCreated phone: $phone")

        if (!mViewModel.updateResponse.hasActiveObservers()) {
            mViewModel.updateResponse.observe(requireActivity(), Observer {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }
                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            CoroutineScope(Dispatchers.Main).launch {
                                dataStoreProvider.saveUserToken(data.token)

//                                    dataStoreProvider.saveDeviceData(randNum!!)
//                                    dataStoreProvider.saveDeviceData("88765275963748185512")
                            }
                            if (LocationPermission.requestPermission(requireContext())) {
                                findNavController().navigate(R.id.action_createPasswordFragment_to_homeFragment)
                            } else {
                                findNavController().navigate(R.id.action_createPasswordFragment_to_locationFragment)
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

    private fun initialization() {
        userNew = mViewDataBinding.userNew.text.toString().trim()
        userConfirmPass = mViewDataBinding.userConfirmPass.text.toString().trim()
    }

    private fun createParams(): JsonObject {
        val params = JsonObject()
        try {
            params.addProperty("contact", phone)
            params.addProperty("password", userConfirmPass.toString())
            params.addProperty("uniqueId", uniqueId)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return params
    }

    private fun isValidate(): Boolean {
        if (mViewDataBinding.userNew.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar("Enter password")
            return false
        }
        if (mViewDataBinding.userConfirmPass.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar("Enter confirm password")
            return false
        }
        if (mViewDataBinding.userNew.text.toString()
                .trim() != mViewDataBinding.userConfirmPass.text.toString().trim()
        ) {
            mViewDataBinding.root.snackbar("Password not matched")
            return false
        }
        return true
    }


}