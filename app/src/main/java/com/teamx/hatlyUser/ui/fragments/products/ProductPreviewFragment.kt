package com.teamx.hatlyUser.ui.fragments.products

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.adapter.HatlyPopularAdapter
import com.teamx.hatlyUser.ui.fragments.products.hatly.ItemClass
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.products.adapter.MultiViewVariationRadioAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductPreviewFragment : BaseFragment<com.teamx.hatlyUser.databinding.FragmentProductPreviewBinding, ProductPreviewViewModel>(),
    HatlyShopInterface {

    override val layoutId: Int
        get() = R.layout.fragment_product_preview
    override val viewModel: Class<ProductPreviewViewModel>
        get() = ProductPreviewViewModel::class.java
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
        mViewDataBinding.textView25.paintFlags = mViewDataBinding.textView25.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

//        val layoutManager1 = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
//        val layoutManager2 = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
//        mViewDataBinding.recCategories.layoutManager = layoutManager1
//        mViewDataBinding.recVariation1.layoutManager = layoutManager2
//
//        val itemClasses: ArrayList<String> = ArrayList()
//
//        val itemClasses1: ArrayList<ItemClass> = ArrayList()
//
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//
//        itemClasses1.add(ItemClass(0,"Required 1"))
//        itemClasses1.add(ItemClass(0,"Required 3"))
//        itemClasses1.add(ItemClass(1,54,"Optional",""))
//        itemClasses1.add(ItemClass(1,54,"Optional",""))
//
//        val hatlyPopularAdapter = HatlyPopularAdapter(itemClasses,this)
////        val prodVariationRadio = ProductVariationRadioAdapter(itemClasses1)
//        val prodVariationRadio = MultiViewVariationRadioAdapter(itemClasses1)
//        mViewDataBinding.recCategories.adapter = hatlyPopularAdapter
//        mViewDataBinding.recVariation1.adapter = prodVariationRadio


    }

    override fun clickshopItem(position: Int) {

    }

    override fun clickMoreItem(position: Int) {

    }


}