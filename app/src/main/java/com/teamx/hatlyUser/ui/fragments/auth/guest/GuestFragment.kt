package com.teamx.hatlyUser.ui.fragments.auth.guest

import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.gson.JsonObject
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.constants.NetworkCallPointsNest
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentGuestBinding
import com.teamx.hatlyUser.utils.LocationPermission
import com.teamx.hatlyUser.utils.PrefHelper
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException


@AndroidEntryPoint
class GuestFragment : BaseFragment<FragmentGuestBinding, GuestViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_guest
    override val viewModel: Class<GuestViewModel>
        get() = GuestViewModel::class.java
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

        mViewDataBinding.txtLogin.setOnClickListener {
            findNavController().navigate(R.id.action_guestFragment_to_signUpFragment)
        }

        mViewDataBinding.textView5.setOnClickListener {
            findNavController().navigate(R.id.action_guestFragment_to_loginFragment)
        }

        mViewDataBinding.textView43.setOnClickListener {
            mViewModel.guest(createParams())
        }


        if (!mViewModel.guestResponse.hasActiveObservers()) {
            mViewModel.guestResponse.observe(requireActivity()) {
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

                            CoroutineScope(Dispatchers.Main).launch {
                                data.token.let { it1 ->
                                    Log.d("loginWithGoogleResponse", "onViewCreated: $it1")
                                    dataStoreProvider.saveUserToken(it1)
                                    NetworkCallPointsNest.TOKENER = it1
                                }

//                                    sharedViewModel.setUserData(data)

//                                    (requireActivity() as ProfileInterFace).profileData(data)
//                                    dataStoreProvider.saveDeviceData(randNum!!)
//                                    dataStoreProvider.saveDeviceData("88765275963748185512")
                                PrefHelper.getInstance(requireActivity()).setUserData(data)
                                if (LocationPermission.requestPermission(requireContext())) {
                                    if (data.location == null) {
                                        findNavController().navigate(R.id.action_guestFragment_to_mapFragment)
                                    } else {
                                        findNavController().navigate(R.id.action_guestFragment_to_homeFragment)
                                    }
                                } else {
                                    findNavController().navigate(R.id.action_guestFragment_to_allowLocationFragment)
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

    private fun createParams(): JsonObject {
        val androidId: String = Settings.Secure.getString(requireContext().contentResolver, Settings.Secure.ANDROID_ID)

        Log.d("handleOnBackPressed", "$androidId")
        val params = JsonObject()
        try {
            params.addProperty("deviceId", androidId)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return params
    }

}