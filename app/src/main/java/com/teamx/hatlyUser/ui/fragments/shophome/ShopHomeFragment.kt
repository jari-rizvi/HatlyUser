package com.teamx.hatlyUser.ui.fragments.shophome

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentShopHomeBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.adapter.HatlyPopularAdapter
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.shophome.adapter.ShopHomeTitleAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ShopHomeFragment : BaseFragment<FragmentShopHomeBinding, ShopHomeViewModel>(),
    HatlyShopInterface {

    override val layoutId: Int
        get() = R.layout.fragment_shop_home
    override val viewModel: Class<ShopHomeViewModel>
        get() = ShopHomeViewModel::class.java
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

        val layoutManager = GridLayoutManager(requireActivity(),2)

        val layoutManager1 = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.recCategories.layoutManager = layoutManager1

        mViewDataBinding.recShopProducts.layoutManager = layoutManager

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

        val adapter = HatlyPopularAdapter(itemClasses, this)
        mViewDataBinding.recShopProducts.adapter = adapter

        val shopHomeAdapter = ShopHomeTitleAdapter(itemClasses)
        mViewDataBinding.recCategories.adapter = shopHomeAdapter


    }

    override fun clickshopItem(position: Int) {
        findNavController().navigate(R.id.action_shopHomeFragment_to_productPreviewFragment)
    }

    override fun clickMoreItem(position: Int) {

    }


}