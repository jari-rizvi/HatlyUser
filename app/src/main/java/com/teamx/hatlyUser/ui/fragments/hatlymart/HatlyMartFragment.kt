package com.teamx.hatlyUser.ui.fragments.hatlymart

import android.os.Bundle
import android.view.View
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentHatlyMartBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.adapter.HatlyMartAdapter
import com.teamx.hatlyUser.ui.fragments.hatlymart.adapter.HatlyShopCatAdapter
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatly.ItemClass
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HatlyMartFragment : BaseFragment<FragmentHatlyMartBinding, HatlyMartViewModel>() {

    override val layoutId: Int
        get() = com.teamx.hatlyUser.R.layout.fragment_hatly_mart
    override val viewModel: Class<HatlyMartViewModel>
        get() = HatlyMartViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        options = navOptions {
            anim {
                enter = com.teamx.hatlyUser.R.anim.enter_from_left
                exit = com.teamx.hatlyUser.R.anim.exit_to_left
                popEnter = com.teamx.hatlyUser.R.anim.nav_default_pop_enter_anim
                popExit = com.teamx.hatlyUser.R.anim.nav_default_pop_exit_anim
            }
        }


        // Create and set the layout manager
        // For the RecyclerView.

        // Create and set the layout manager
        // For the RecyclerView.
        val layoutManager = GridLayoutManager(requireActivity(),4)
        mViewDataBinding.recShopCatMart.layoutManager = layoutManager
        val layoutManager1 = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.recBasedMart.layoutManager = layoutManager1

        val itemClasses: ArrayList<String> = ArrayList()

        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")

        val adapter = HatlyShopCatAdapter(itemClasses)

        // set the adapter

        // set the adapter
        mViewDataBinding.recShopCatMart.adapter = adapter
        mViewDataBinding.recBasedMart.adapter = adapter

    }


}