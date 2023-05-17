package com.teamx.hatlyUser.ui.fragments.onboard

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.navOptions
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentOnboardViewpagerBinding
import com.teamx.hatlyUser.ui.fragments.onboard.adapter.DemoCollectionAdapter
import com.teamx.hatlyUser.ui.fragments.onboard.fragments.Delivery
import com.teamx.hatlyUser.ui.fragments.onboard.fragments.Dishes
import com.teamx.hatlyUser.ui.fragments.onboard.fragments.Restaurant
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BoardViewPagerFragment :
    BaseFragment<FragmentOnboardViewpagerBinding, BoardViewpagerViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_onboard_viewpager
    override val viewModel: Class<BoardViewpagerViewModel>
        get() = BoardViewpagerViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    lateinit var featureProductArrayList: ArrayList<Fragment>

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

        featureProductArrayList = ArrayList()

        featureProductArrayList.add(Restaurant())
        featureProductArrayList.add(Dishes())
        featureProductArrayList.add(Delivery())

        val adapter = DemoCollectionAdapter(
            requireActivity().supportFragmentManager,
            requireActivity().lifecycle, featureProductArrayList
        )
        mViewDataBinding.viewPager.adapter = adapter

        TabLayoutMediator(mViewDataBinding.tabLayout, mViewDataBinding.viewPager) { tab, position ->
//            tab.text = "OBJECT ${(position + 1)}"
        }.attach()

        mViewDataBinding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    2 -> {
                        mViewDataBinding.txtGetStarted.visibility = View.VISIBLE
                        mViewDataBinding.tabLayout.visibility = View.VISIBLE
                    }
                    else -> {
                        mViewDataBinding.txtGetStarted.visibility = View.GONE
                        mViewDataBinding.tabLayout.visibility = View.VISIBLE
                    }
                }
                super.onPageSelected(position)
            }
        })

    }


}