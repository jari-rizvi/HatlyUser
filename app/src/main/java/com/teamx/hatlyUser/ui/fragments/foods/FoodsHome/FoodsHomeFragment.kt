package com.teamx.hatlyUser.ui.fragments.foods.FoodsHome

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
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
import com.teamx.hatlyUser.ui.fragments.hatlymart.stores.bottomsheet.BottomSheetRatDelListener
import com.teamx.hatlyUser.ui.fragments.hatlymart.stores.bottomsheet.BottomSheetRatingDeliveryFragment
import com.teamx.hatlyUser.utils.enum_.Marts
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FoodsHomeFragment : BaseFragment<FragmentFoodsHomeBinding, FoodsHomeViewModel>(),
    HatlyShopInterface, BottomSheetRatDelListener {

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

    private var categoryTitle = ""

    private var isDelivery = true

    private lateinit var bottomSheetAddSearchFragment: BottomSheetRatingDeliveryFragment

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

        mViewDataBinding.txtDelivery.setOnClickListener {
            bottomSheetAddSearchFragment = BottomSheetRatingDeliveryFragment()
            bottomSheetAddSearchFragment.setBottomSheetListener(this)
            if (!bottomSheetAddSearchFragment.isAdded) {
                val bundle = Bundle()
                bundle.putBoolean("isDelivery", true)
                deliverTime?.let { it1 -> bundle.putInt("selectedValue", it1) }
                Log.d("selectedValue", "onViewCreated: ${deliverTime}")
                isDelivery = true
                bottomSheetAddSearchFragment.arguments = bundle
                bottomSheetAddSearchFragment.show(
                    parentFragmentManager,
                    bottomSheetAddSearchFragment.tag
                )
            }
        }

        mViewDataBinding.txtRating.setOnClickListener {
            bottomSheetAddSearchFragment = BottomSheetRatingDeliveryFragment()
            bottomSheetAddSearchFragment.setBottomSheetListener(this)
            if (!bottomSheetAddSearchFragment.isAdded) {
                val bundle = Bundle()
                bundle.putBoolean("isDelivery", false)
                rating?.let { it1 -> bundle.putInt("selectedValue", it1) }
                Log.d("selectedValue", "onViewCreated: ${rating}")
                isDelivery = false
                bottomSheetAddSearchFragment.arguments = bundle
                bottomSheetAddSearchFragment.show(
                    parentFragmentManager,
                    bottomSheetAddSearchFragment.tag
                )
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
                    mViewModel.allFoodsCategories(1, 10)
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
                Resource.Status.AUTH -> {
                    loadingDialog.dismiss()
                    onToSignUpPage()
                }

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

                    }
                }

                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    if (isAdded) {

                        mViewDataBinding.mainRoot.snackbar(it.message!!)
                    }
                }
            }
        }

        if (!mViewModel.allFoodsShopsResponse.hasActiveObservers()) {
            mViewModel.allFoodsShops(1, 10, 0, searchshop, categoryId, deliverTime, rating)
        }

        mViewModel.allFoodsShopsResponse.observe(requireActivity()) {
            when (it.status) {
                Resource.Status.AUTH -> {
                    loadingDialog.dismiss()
                    onToSignUpPage()
                }

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
                    if (isAdded) {

                        mViewDataBinding.mainRoot.snackbar(it.message!!)
                    }
                }
            }
        }



        layoutManager = GridLayoutManager(requireActivity(), 2)
        mViewDataBinding.recFoodHomeProducts.layoutManager = layoutManager


//        foodScrooling()

        foodHomeAdapter = FoodHomeAdapter(foodsAllShopsArrayList, this)
        mViewDataBinding.recFoodHomeProducts.adapter = foodHomeAdapter


        mViewDataBinding.inpSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                searchshop = mViewDataBinding.inpSearch.text.toString().trim()
                mViewModel.allFoodsShops(1, 10, 0, searchshop, categoryId, deliverTime, rating)

            }
            true
        }


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

    private var categoryId: String? = null
    private var searchshop = ""

    override fun clickCategoryItem(position: Int) {
        foodsCategoryArrayList.forEach { it.itemSelected = false }
        val categoryModel = foodsCategoryArrayList[position]
        categoryTitle = categoryModel.title
        mViewDataBinding.txtShopCatTitle.text = "$categoryTitle Restaurants"
        foodsCategoryArrayList[position].itemSelected = true
        foodHomeCategoryAdapter.notifyDataSetChanged()
        categoryId = categoryModel._id
        mViewModel.allFoodsShops(1, 10, 0, searchshop, categoryId, deliverTime, rating)
    }

    override fun clickshopItem(position: Int) {
        val foodShopModel = foodsAllShopsArrayList[position]
        val bundle = Bundle()
        bundle.putString("itemId", foodShopModel._id)
        findNavController().navigate(R.id.action_foodsHomeFragment_to_foodsShopHomeFragment, bundle)
    }

    override fun clickMoreItem(position: Int) {

    }

    private var deliverTime: Int? = null
    private var rating: Int? = null

    override fun onBottomSheetratDel(value: Int?) {
        when {
            isDelivery -> {
                deliverTime = value
                if (value == null) {
                    mViewDataBinding.txtDelivery.text = "Delivery Distance"
                    mViewDataBinding.txtDelivery.isChecked = false
                } else {
                    mViewDataBinding.txtDelivery.text = "Under $value mins"
                    mViewDataBinding.txtDelivery.isChecked = true
                }
            }

            else -> {
                rating = value
                if (value == null) {
                    mViewDataBinding.txtRating.text = "Ratings"
                    mViewDataBinding.txtRating.isChecked = false
                } else {
                    mViewDataBinding.txtRating.text = "Rating ${value}.0+"
                    mViewDataBinding.txtRating.isChecked = true
                }

            }
        }

        mViewModel.allFoodsShops(1, 10, 0, searchshop, categoryId, deliverTime, rating)
    }


}