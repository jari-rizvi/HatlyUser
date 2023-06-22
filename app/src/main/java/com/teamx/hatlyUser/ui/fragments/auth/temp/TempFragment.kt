package com.teamx.hatlyUser.ui.fragments.auth.temp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.constants.NetworkCallPointsNest.Companion.DEVICE_TOKEN
import com.teamx.hatlyUser.constants.NetworkCallPointsNest.Companion.TOKENER
import com.teamx.hatlyUser.databinding.FragmentTempBinding
import com.teamx.hatlyUser.utils.LocationPermission
import com.teamx.hatlyUser.utils.PrefHelper
import com.teamx.hatlyUser.utils.SlideToUnlock
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TempFragment : BaseFragment<FragmentTempBinding, TempViewModel>(),
    SlideToUnlock.OnSlideToUnlockEventListener {

    override val layoutId: Int
        get() = R.layout.fragment_temp
    override val viewModel: Class<TempViewModel>
        get() = TempViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

//    private lateinit var options: NavOptions

    var isNotFirstTime = false
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
        isNotFirstTime = PrefHelper.getInstance(requireActivity()).isNotFirstTime

        mViewDataBinding.slideToUnlock.externalListener = this

        if (isNotFirstTime) {
            mViewDataBinding.layoutMain.visibility = View.GONE
            Handler(Looper.getMainLooper()).postDelayed({
                checkLoginStatus()
            }, 5000)
        } else {
            mViewDataBinding.layoutMain.visibility = View.VISIBLE
        }
    }

    override fun onSlideToUnlockCanceled() {
        Log.d("slider", "onSlideToUnlockCanceled: ")
    }

    override fun onSlideToUnlockDone() {
        Log.d("slider", "onSlideToUnlockDone: ")
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        if (isAdded) {
            var token: String? = null
            CoroutineScope(Dispatchers.Main).launch {
                Log.d("allStoresResponse", "Dispatchers")
                dataStoreProvider.token.collect {
                    Log.d("allStoresResponse", "Dispatchers token $it")
                    token = it
                    /*NetworkCallPointsNest.*/
                    TOKENER = token.toString()

                    dataStoreProvider.deviceDATA.collect {
                        Log.d("allStoresResponse", "Dispatchers deviceDATA $it")
                        DEVICE_TOKEN = it
                        if (isAdded) {
                            if (token.isNullOrBlank()) {
                                if (isNotFirstTime) {
                                    findNavController().navigate(R.id.action_tempFragment_to_loginFragment)
                                } else {
                                    findNavController().navigate(R.id.action_tempFragment_to_onboardViewPagerFragment)
                                }
                            } else {
                                if (LocationPermission.requestPermission(requireContext())) {
                                    findNavController().navigate(R.id.action_tempFragment_to_homeFragment)
                                } else {
                                    findNavController().navigate(R.id.action_tempFragment_to_allowLocationFragment)
                                }
                            }
                        }

                    }
                }
            }
        }
    }

}