package com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentFoodsHomeBinding
import com.teamx.hatlyUser.databinding.FragmentFoodsShopPreviewBinding
import com.teamx.hatlyUser.ui.fragments.foods.FoodsHome.adapter.FoodHomeAdapter
import com.teamx.hatlyUser.ui.fragments.foods.FoodsHome.adapter.FoodHomeTitleAdapter
import com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.adapter.FoodsShopProductAdapter
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.shophome.adapter.ShopHomeTitleAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FoodsShopPreviewFragment :
    BaseFragment<FragmentFoodsShopPreviewBinding, FoodsShopPreviewViewModel>(),
    HatlyShopInterface {

    override val layoutId: Int
        get() = R.layout.fragment_foods_shop_preview
    override val viewModel: Class<FoodsShopPreviewViewModel>
        get() = FoodsShopPreviewViewModel::class.java
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

        mViewDataBinding.linearLayout.setOnClickListener {
            findNavController().navigate(R.id.action_foodsShopHomeFragment_to_reviewFragment)
        }

        val layoutManager1 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.recCategories.layoutManager = layoutManager1

        val layoutManager2 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.recShopProducts.layoutManager = layoutManager2

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
        itemClasses.add("Hello")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")

        Log.d("itemClasses", "onViewCreated: ${itemClasses.binarySearch("hello")}")

        val adapter = FoodsShopProductAdapter(itemClasses, this)
        mViewDataBinding.recShopProducts.adapter = adapter

        val shopHomeAdapter = ShopHomeTitleAdapter(itemClasses)
        mViewDataBinding.recCategories.adapter = shopHomeAdapter


    }

    override fun clickshopItem(position: Int) {
        findNavController().navigate(R.id.action_foodsShopHomeFragment_to_productPreviewFragment)
    }

    override fun clickMoreItem(position: Int) {

    }


}