package com.teamx.hatlyUser.ui.fragments.profile.changepassword

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentChangePasswordBinding
import com.teamx.hatlyUser.databinding.FragmentLocationBinding
import com.teamx.hatlyUser.ui.fragments.auth.login.Model.Location
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.payments.checkout.PaymentMethod
import com.teamx.hatlyUser.ui.fragments.profile.Locations.adapter.LocationsListAdapter
import com.teamx.hatlyUser.utils.PrefHelper
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException


@AndroidEntryPoint
class ChangePasswordFragment :
    BaseFragment<FragmentChangePasswordBinding, ChangePasswordViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_change_password
    override val viewModel: Class<ChangePasswordViewModel>
        get() = ChangePasswordViewModel::class.java
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

        mViewDataBinding.txtAddLocation.setOnClickListener {
            mViewModel.changePassword(createJson())
        }


        if (!mViewModel.changePasswordResponse.hasActiveObservers()) {
            mViewModel.changePasswordResponse.observe(requireActivity()) {
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

                            if (data.success){
                                if (isAdded){
                                    findNavController().popBackStack()
                                    mViewDataBinding.root.snackbar(data.message)
                                }
                            }
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        if (isAdded) {

                        mViewDataBinding.root.snackbar(it.message!!)
                        }
                    }
                }
            }
        }


    }

    private fun createJson(): JsonObject {
        val params = JsonObject()
        try {
            if (mViewDataBinding.inpCurrentPass.text.isNotEmpty()) {
                params.addProperty("oldPassword", mViewDataBinding.inpCurrentPass.text.toString())
            }

            if (mViewDataBinding.inpNewPass.text.isNotEmpty()) {
                params.addProperty("password", mViewDataBinding.inpNewPass.text.toString())
            }

            if (mViewDataBinding.inpConfirmPass.text.isNotEmpty()) {
                params.addProperty("confirmPassword", mViewDataBinding.inpConfirmPass.text.toString())
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return params
    }
}