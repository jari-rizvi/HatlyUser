package com.teamx.hatlyUser.ui.fragments.onboard.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.navOptions
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentDeliveryBinding
import com.teamx.hatlyUser.ui.fragments.onboard.BoardViewpagerViewModel
import com.teamx.hatlyUser.ui.fragments.onboard.adapter.DemoCollectionAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Delivery : BaseFragment<FragmentDeliveryBinding, BoardViewpagerViewModel>(){

    override val layoutId: Int
        get() = R.layout.fragment_delivery
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