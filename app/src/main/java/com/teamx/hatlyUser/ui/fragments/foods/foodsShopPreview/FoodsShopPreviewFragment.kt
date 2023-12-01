package com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.constants.NetworkCallPointsNest
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentFoodsShopPreviewBinding
import com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.adapter.FoodHomeTitleAdapter
import com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.adapter.FoodsShopProductAdapter
import com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.modelShopHome.Document
import com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.modelShopHome.Product
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.utils.enum_.Marts
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException


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

    private lateinit var shopCategoryArrayList: ArrayList<Product>
    private lateinit var productArrayList: ArrayList<Document>

    //    private lateinit var foodsShopProductAdapter: FoodsShopProductAdapter
    private lateinit var shopHomeAdapter: FoodHomeTitleAdapter

    private var productLayoutManager2: LinearLayoutManager? = null
    var categoryLayoutManager: GridLayoutManager? = null

    var shopId = ""

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

        mViewDataBinding.imgShare.setOnClickListener {
            shareText("Hey, I discovered this restaurant on Hatly. The food looks delicious! Check it out.\nhttps://hatly.ae/")
        }

        mViewDataBinding.imgSearch.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("itemId", shopId)
            findNavController().navigate(
                R.id.action_foodsShopHomeFragment_to_foodSearchFragment,
                bundle
            )
        }

        when (NetworkCallPointsNest.MART) {
            Marts.HATLY_MART -> {
                Log.d("StoreFragment", "HATLY_MART: back")
            }

            Marts.FOOD -> {
                Log.d("StoreFragment", "FOOD: back")
                val bundle = arguments
                if (bundle != null) {
                    val foodShopId = bundle.getString("itemId").toString()
                    if (!mViewModel.foodsShopHomeResponse.hasActiveObservers()) {
                        mViewModel.foodsShopHome(foodShopId)
                    }
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


        mViewDataBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        mViewDataBinding.linearLayout.setOnClickListener {
            if (shopId.isNotEmpty()) {
                val bundle = Bundle()
                bundle.putString("shopId", shopId)
                findNavController().navigate(
                    R.id.action_foodsShopHomeFragment_to_reviewFragment,
                    bundle
                )
            }
        }

        mViewDataBinding.imgFavourite.setOnClickListener {
            if (shopId.isNotEmpty()) {
                val params = JsonObject()
                try {
                    params.addProperty("itemId", shopId)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                mViewModel.favRemove(params)
            }
        }


//        Log.d("itemClasses", "onViewCreated: ${shopShopArrayList.binarySearch("hello")}")


        initializeCategoriesAdapter()
        initializeproductAdapter()

        mViewModel.foodsShopHomeResponse.observe(requireActivity())
        {
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

                        shopId = data.shop._id

                        Picasso.get().load(data.shop.image).resize(500, 500)
                            .into(mViewDataBinding.imgShop)

                        mViewDataBinding.imgFavourite.isChecked = data.shop.hasAddedToWishlist

                        mViewDataBinding.textView19.text = try {
                            data.shop.name
                        } catch (e: Exception) {
                            "null"
                        }

                        mViewDataBinding.txtDelivery.text = try {
                            "Delivery ${data.shop.delivery.value} ${data.shop.delivery.unit}"
                        } catch (e: Exception) {
                            "null"
                        }

                        val rattingSum = data.shop.ratting
                        mViewDataBinding.shopRate.rating = rattingSum.toFloat()
                        mViewDataBinding.txtRating.text = try {
                            rattingSum.toString()
                        } catch (e: Exception) {
                            "null"
                        }

                        if (data.shop.isOpen) {
                            mViewDataBinding.txtIsOpen.text = "Open now"
                        } else {
                            mViewDataBinding.txtIsOpen.text = "Closed"
                        }

                        shopCategoryArrayList.clear()
                        shopCategoryArrayList.addAll(data.products)
                        shopHomeAdapter.notifyDataSetChanged()
                        if (data.products.isNotEmpty()) {
                            clickCategoryItem(0)
                        }
                    }
                }

                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    if (isAdded) {

                        mViewDataBinding.root.snackbar(it.message!!)
                    }
                }
            }
        }


        if (!mViewModel.favRemoveResponse.hasActiveObservers()) {
            mViewModel.favRemoveResponse.observe(requireActivity()) {
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
                            if (data.success) {
                                mViewDataBinding.imgFavourite.isChecked =
                                    !mViewDataBinding.imgFavourite.isChecked
                                if (isAdded) {
                                    if (mViewDataBinding.imgFavourite.isChecked) {
                                        mViewDataBinding.root.snackbar("Product added in wishlist")
                                    } else {
                                        mViewDataBinding.root.snackbar("Product remove from wishlist")
                                    }
                                }
                            }
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        if (isAdded) {

                            mViewDataBinding.root.snackbar(it.message!!)
                        }
                    }
                }
            }
        }


    }

    private fun initializeCategoriesAdapter() {
        shopCategoryArrayList = ArrayList()
        val layoutManager1 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.recCategories.layoutManager = layoutManager1

        shopHomeAdapter = FoodHomeTitleAdapter(shopCategoryArrayList, this)
        mViewDataBinding.recCategories.adapter = shopHomeAdapter
    }

    private fun initializeproductAdapter() {
        productArrayList = ArrayList()


//        mViewDataBinding.recShopProducts.addOnScrollListener(object :
//            RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
//                    isScrolling = true
//                }
//            }
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//                currentItems = productLayoutManager2!!.childCount
//                totalItems = productLayoutManager2!!.itemCount
//                scrollOutItems = productLayoutManager2!!.findFirstVisibleItemPosition()
//
//                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
//                    isScrolling = false
////                    fetchData()
//                }
//            }
//        })
    }

//    private fun fetchData() {
//        mViewDataBinding.spinKit.visibility = View.VISIBLE
//        Handler(Looper.getMainLooper()).postDelayed({
//            for (i in 1..5) {
//                shopShopArrayList.add("")
//                shopShopArrayList.add("")
//                shopShopArrayList.add("")
//                shopShopArrayList.add("")
//                shopShopArrayList.add("")
//                shopShopArrayList.add("")
//                shopShopArrayList.add("")
//                shopShopArrayList.add("")
//                shopShopArrayList.add("")
//                shopShopArrayList.add("")
//                shopShopArrayList.add("")
//            }
//            mViewDataBinding.spinKit.visibility = View.GONE
//            foodsShopProductAdapter.notifyDataSetChanged()
//        }, 5000)
//
//
//    }

    override fun clickshopItem(position: Int) {
        val modelProduct = productArrayList[position]
        val bundle = Bundle()
        bundle.putString("_id", modelProduct._id)
        bundle.putString("name", modelProduct.name)
        findNavController().navigate(
            R.id.action_foodsShopHomeFragment_to_productPreviewFragment,
            bundle
        )
    }

    override fun clickCategoryItem(position: Int) {

        shopCategoryArrayList.forEach { it.isSelected = false }

        shopCategoryArrayList[position].isSelected = true

        shopHomeAdapter.notifyDataSetChanged()

        productArrayList.clear()
        productArrayList.addAll(shopCategoryArrayList[position].documents)

        productLayoutManager2 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.recShopProducts.layoutManager = productLayoutManager2

        val foodsShopProductAdapter = FoodsShopProductAdapter(productArrayList, this)
        mViewDataBinding.recShopProducts.adapter = foodsShopProductAdapter


    }

    override fun clickMoreItem(position: Int) {
    }

    fun shareText(body: String?) {
        val txtIntent = Intent(Intent.ACTION_SEND)
        txtIntent.type = "text/plain"
        txtIntent.putExtra(Intent.EXTRA_TEXT, body)
        startActivity(Intent.createChooser(txtIntent, "Share"))
    }


}