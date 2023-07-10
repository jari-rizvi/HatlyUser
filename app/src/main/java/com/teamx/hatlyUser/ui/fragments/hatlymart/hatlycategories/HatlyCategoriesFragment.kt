package com.teamx.hatlyUser.ui.fragments.hatlymart.hatlycategories

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentHatlyCategoriesBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.adapter.HatlyShopCatAdapter
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HatlyCategoriesFragment :
    BaseFragment<FragmentHatlyCategoriesBinding, HatlyCategoriesViewModel>(), HatlyShopInterface {

    override val layoutId: Int
        get() = R.layout.fragment_hatly_categories
    override val viewModel: Class<HatlyCategoriesViewModel>
        get() = HatlyCategoriesViewModel::class.java
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

        mViewDataBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

//        val layoutManager = GridLayoutManager(requireActivity(), 4)
//        mViewDataBinding.recShopCatMart.layoutManager = layoutManager
//
//        val itemClasses: ArrayList<String> = ArrayList()
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
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//
//        val adapter = HatlyShopCatAdapter(itemClasses, this)
//        mViewDataBinding.recShopCatMart.adapter = adapter


    }

    override fun clickshopItem(position: Int) {
        findNavController().navigate(R.id.action_HatlyCategoriesFragment_to_ShopHomeFragment)
    }

    override fun clickMoreItem(position: Int) {

    }


}