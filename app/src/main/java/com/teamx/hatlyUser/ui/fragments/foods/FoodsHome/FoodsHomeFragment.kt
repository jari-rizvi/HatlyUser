package com.teamx.hatlyUser.ui.fragments.foods.FoodsHome

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.AbsListView
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    lateinit var itemClasses: ArrayList<String>
    lateinit var foodHomeAdapter: FoodHomeAdapter

    var layoutManager2 : LinearLayoutManager? = null
    var layoutManager : GridLayoutManager? = null

    var isScrolling = false
    var currentItems = 0
    var totalItems = 0
    var scrollOutItems = 0

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

        layoutManager = GridLayoutManager(requireActivity(),2)

        layoutManager2 = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.recFoodTitle.layoutManager = layoutManager2

        mViewDataBinding.recFoodHomeProducts.layoutManager = layoutManager

        itemClasses = ArrayList()

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

        foodHomeAdapter = FoodHomeAdapter(itemClasses, this)
        mViewDataBinding.recFoodHomeProducts.adapter = foodHomeAdapter

        val foodHomeTitleAdapterAdapter = FoodHomeTitleAdapter(itemClasses)
        mViewDataBinding.recFoodTitle.adapter = foodHomeTitleAdapterAdapter

        mViewDataBinding.recFoodHomeProducts.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                currentItems = layoutManager!!.childCount
                totalItems = layoutManager!!.itemCount
                scrollOutItems = layoutManager!!.findFirstVisibleItemPosition()

                if(isScrolling && (currentItems + scrollOutItems == totalItems))
                {
                    isScrolling = false
                    fetchData()
                }
            }
        })

    }

    private fun fetchData(){
        mViewDataBinding.spinKit.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            for (i in 1..5) {
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
            }
            mViewDataBinding.spinKit.visibility = View.GONE
            foodHomeAdapter.notifyDataSetChanged()
        }, 5000)


    }

    override fun clickshopItem(position: Int) {
        findNavController().navigate(R.id.action_foodsHomeFragment_to_foodsShopHomeFragment)
    }

    override fun clickMoreItem(position: Int) {

    }


}