package com.teamx.hatlyUser.ui.fragments.hatlymart

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.MainApplication
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentHatlyMartBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.adapter.HatlyMartAdapter
import com.teamx.hatlyUser.ui.fragments.hatlymart.adapter.HatlyPopularAdapter
import com.teamx.hatlyUser.ui.fragments.hatlymart.adapter.HatlyShopCatAdapter
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatly.ItemClass
import com.teamx.hatlyUser.ui.fragments.hatlymart.interfaces.HatlyShopInterface
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HatlyMartFragment : BaseFragment<FragmentHatlyMartBinding, HatlyMartViewModel>(), HatlyShopInterface {

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
        val layoutManager2 = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.recBasedMart.layoutManager = layoutManager1
        mViewDataBinding.recPopular.layoutManager = layoutManager2

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
        itemClasses.add("More\ncategories")

        val adapter = HatlyShopCatAdapter(itemClasses, this)
        val hatlyPopularAdapter = HatlyPopularAdapter(itemClasses,this)

        // set the adapter

        // set the adapter
        mViewDataBinding.recShopCatMart.adapter = adapter
        mViewDataBinding.recBasedMart.adapter = adapter
        mViewDataBinding.recPopular.adapter = hatlyPopularAdapter

    }

    override fun clickshopItem(position: Int) {
        findNavController().navigate(R.id.action_hatlyMartFragment_to_ShopHomeFragment)
        Toast.makeText(MainApplication.context, "Shop", Toast.LENGTH_SHORT).show()
    }

    override fun clickMoreItem(position: Int) {
        findNavController().navigate(R.id.action_hatlyMartFragment_to_HatlyCategoriesFragment)
        Toast.makeText(MainApplication.context, "More", Toast.LENGTH_SHORT).show()
    }


}