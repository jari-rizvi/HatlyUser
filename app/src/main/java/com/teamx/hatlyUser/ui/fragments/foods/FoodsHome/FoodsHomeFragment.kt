package com.teamx.hatlyUser.ui.fragments.foods.FoodsHome

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
import com.teamx.hatlyUser.constants.NetworkCallPointsNest
import com.teamx.hatlyUser.databinding.FragmentFoodsHomeBinding
import com.teamx.hatlyUser.ui.fragments.foods.FoodsHome.adapter.FoodHomeAdapter
import com.teamx.hatlyUser.ui.fragments.foods.FoodsHome.adapter.FoodHomeTitleAdapter
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.utils.enum_.Marts
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FoodsHomeFragment : BaseFragment<FragmentFoodsHomeBinding, FoodsHomeViewModel>(),
    HatlyShopInterface {

    override val layoutId: Int
        get() = R.layout.fragment_foods_home
    override val viewModel: Class<FoodsHomeViewModel>
        get() = FoodsHomeViewModel::class.java
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

        when (NetworkCallPointsNest.MART){
            Marts.HATLY_MART -> {
                Log.d("StoreFragment", "HATLY_MART: back")
            }
            Marts.FOOD -> {
                Log.d("StoreFragment", "FOOD: back")
            }
            Marts.GROCERY -> {
                Log.d("StoreFragment", "GROCERY: back")
            }
            Marts.HEALTH_BEAUTY -> {
                Log.d("StoreFragment", "HEALTH_BEAUTY: back")
            }
            Marts.HOME_BUSINESS -> {
                Log.d("StoreFragment", "HOME_BUSINESS: back")
            }
        }

        val layoutManager = GridLayoutManager(requireActivity(),2)

        val layoutManager1 = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.recFoodTitle.layoutManager = layoutManager1

        mViewDataBinding.recFoodHomeProducts.layoutManager = layoutManager

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

        val adapter = FoodHomeAdapter(itemClasses, this)
        mViewDataBinding.recFoodHomeProducts.adapter = adapter

        val foodHomeTitleAdapterAdapter = FoodHomeTitleAdapter(itemClasses)
        mViewDataBinding.recFoodTitle.adapter = foodHomeTitleAdapterAdapter


    }

    override fun clickshopItem(position: Int) {
        findNavController().navigate(R.id.action_foodsHomeFragment_to_foodsShopHomeFragment)
    }

    override fun clickMoreItem(position: Int) {

    }


}