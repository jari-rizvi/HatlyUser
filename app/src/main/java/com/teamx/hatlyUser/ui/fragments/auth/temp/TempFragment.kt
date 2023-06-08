package com.teamx.hatlyUser.ui.fragments.auth.temp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentTempBinding
import com.teamx.hatlyUser.utils.SlideToUnlock
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TempFragment : BaseFragment<FragmentTempBinding, TempViewModel>(), SlideToUnlock.OnSlideToUnlockEventListener {

    override val layoutId: Int
        get() = R.layout.fragment_temp
    override val viewModel: Class<TempViewModel>
        get() = TempViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

//    private lateinit var options: NavOptions


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

        mViewDataBinding.slideToUnlock.externalListener = this
    }

    override fun onSlideToUnlockCanceled() {
        Log.d("slider", "onSlideToUnlockCanceled: ")
    }

    override fun onSlideToUnlockDone() {
        Log.d("slider", "onSlideToUnlockDone: ")
        findNavController().navigate(R.id.action_tempFragment_to_onboardViewPagerFragment)
    }

}