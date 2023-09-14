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
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentFoodsHomeBinding
import com.teamx.hatlyUser.ui.fragments.foods.FoodsHome.adapter.FoodHomeAdapter
import com.teamx.hatlyUser.ui.fragments.foods.FoodsHome.adapter.FoodHomeCategoryAdapter
import com.teamx.hatlyUser.ui.fragments.foods.FoodsHome.models.modelCategory.Doc
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.utils.enum_.Marts
import com.teamx.hatlyUser.utils.snackbar
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

    lateinit var foodsCategoryArrayList: ArrayList<Doc>
    lateinit var foodHomeCategoryAdapter: FoodHomeCategoryAdapter
    lateinit var foodsAllShopsArrayList: ArrayList<com.teamx.hatlyUser.ui.fragments.foods.FoodsHome.models.modelShops.Doc>
    lateinit var foodHomeAdapter: FoodHomeAdapter

    var categoryLayoutManager: LinearLayoutManager? = null
    var layoutManager: GridLayoutManager? = null

    var isScrolling = false
    var currentItems = 0
    var totalItems = 0
    var scrollOutItems = 0

    var categoryTitle = ""

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

        foodsAllShopsArrayList = ArrayList()
        foodsCategoryArrayList = ArrayList()

        categoryLayoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.recFoodTitle.layoutManager = categoryLayoutManager

        foodHomeCategoryAdapter = FoodHomeCategoryAdapter(foodsCategoryArrayList, this)
        mViewDataBinding.recFoodTitle.adapter = foodHomeCategoryAdapter

        mViewDataBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        when (NetworkCallPointsNest.MART) {
            Marts.HATLY_MART -> {
                Log.d("StoreFragment", "HATLY_MART: back")
            }
            Marts.FOOD -> {
                Log.d("StoreFragment", "FOOD: back")
                if (!mViewModel.allFoodsCategoriesResponse.hasActiveObservers()) {
                    mViewModel.allFoodsCategories(1, 10, 0)
                }
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


        mViewModel.allFoodsCategoriesResponse.observe(requireActivity()) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }

                Resource.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    it.data?.let { data ->
                        foodsCategoryArrayList.clear()
                        Log.d("allStoresResponse", "onViewCreated: $data")
                        foodsCategoryArrayList.addAll(data.docs)
                        foodHomeCategoryAdapter.notifyDataSetChanged()

//                        mViewModel.allFoodsShops(1, 10, 0, "", data.docs[0].title)
                    }
                }

                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    mViewDataBinding.root.snackbar(it.message!!)
                }
            }
        }

        mViewModel.allFoodsShops(1, 10, 0, "", categoryTitle)

        mViewModel.allFoodsShopsResponse.observe(requireActivity()) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }

                Resource.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    it.data?.let { data ->
                        foodsAllShopsArrayList.clear()
                        Log.d("allStoresResponse", "onViewCreated: $data")
                        foodsAllShopsArrayList.addAll(data.docs)

                        foodHomeAdapter.notifyDataSetChanged()
                    }
                }

                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    mViewDataBinding.root.snackbar(it.message!!)
                }
            }
        }



        layoutManager = GridLayoutManager(requireActivity(), 2)
        mViewDataBinding.recFoodHomeProducts.layoutManager = layoutManager


//        foodScrooling()

        foodHomeAdapter = FoodHomeAdapter(foodsAllShopsArrayList, this)
        mViewDataBinding.recFoodHomeProducts.adapter = foodHomeAdapter


    }

    private fun foodScrooling() {
        mViewDataBinding.recFoodTitle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                currentItems = layoutManager!!.childCount
                totalItems = layoutManager!!.itemCount
                scrollOutItems = layoutManager!!.findFirstVisibleItemPosition()

                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false
//                    fetchData()
                }
            }
        })
    }

//    private fun fetchData(){
//        mViewDataBinding.spinKit.visibility = View.VISIBLE
//        Handler(Looper.getMainLooper()).postDelayed({
//            for (i in 1..5) {
//                foodsCategoryArrayList.add("")
//                foodsCategoryArrayList.add("")
//                foodsCategoryArrayList.add("")
//                foodsCategoryArrayList.add("")
//                foodsCategoryArrayList.add("")
//                foodsCategoryArrayList.add("")
//                foodsCategoryArrayList.add("")
//                foodsCategoryArrayList.add("")
//                foodsCategoryArrayList.add("")
//                foodsCategoryArrayList.add("")
//                foodsCategoryArrayList.add("")
//            }
//            mViewDataBinding.spinKit.visibility = View.GONE
//            foodHomeAdapter.notifyDataSetChanged()
//        }, 5000)
//
//
//    }

    override fun clickshopItem(position: Int) {
        findNavController().navigate(R.id.action_foodsHomeFragment_to_foodsShopHomeFragment)
    }

    override fun clickCategoryItem(position: Int) {
        val categoryIte = foodsCategoryArrayList[position]
        categoryTitle = categoryIte.title
        mViewModel.allFoodsShops(1, 10, 0, "", categoryTitle)
    }

    override fun clickMoreItem(position: Int) {

    }


}