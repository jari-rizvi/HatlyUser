package com.teamx.hatlyUser.ui.fragments.onboard

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.navOptions
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentOnboardViewpagerBinding
import com.teamx.hatlyUser.utils.SlideToUnlock
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BoardViewPagerFragment : BaseFragment<FragmentOnboardViewpagerBinding, BoardViewpagerViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_onboard_viewpager
    override val viewModel: Class<BoardViewpagerViewModel>
        get() = BoardViewpagerViewModel::class.java
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


    }


}