package com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview

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
import com.teamx.hatlyUser.databinding.FragmentFoodsShopPreviewBinding
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

    lateinit var itemClasses: ArrayList<String>
    lateinit var foodsShopProductAdapter: FoodsShopProductAdapter

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

        mViewDataBinding.linearLayout.setOnClickListener {
            findNavController().navigate(R.id.action_foodsShopHomeFragment_to_reviewFragment)
        }

        val layoutManager1 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.recCategories.layoutManager = layoutManager1

        layoutManager2 = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.recShopProducts.layoutManager = layoutManager2

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
        itemClasses.add("Hello")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")

        Log.d("itemClasses", "onViewCreated: ${itemClasses.binarySearch("hello")}")

        foodsShopProductAdapter = FoodsShopProductAdapter(itemClasses, this)
        mViewDataBinding.recShopProducts.adapter = foodsShopProductAdapter

        val shopHomeAdapter = ShopHomeTitleAdapter(itemClasses)
        mViewDataBinding.recCategories.adapter = shopHomeAdapter

        mViewDataBinding.recShopProducts.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                currentItems = layoutManager2!!.childCount
                totalItems = layoutManager2!!.itemCount
                scrollOutItems = layoutManager2!!.findFirstVisibleItemPosition()

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
            foodsShopProductAdapter.notifyDataSetChanged()
        }, 5000)


    }

    override fun clickshopItem(position: Int) {
        findNavController().navigate(R.id.action_foodsShopHomeFragment_to_productPreviewFragment)
    }

    override fun clickMoreItem(position: Int) {

    }


}