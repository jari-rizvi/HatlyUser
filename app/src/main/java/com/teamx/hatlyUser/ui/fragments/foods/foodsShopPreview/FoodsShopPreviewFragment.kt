package com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.constants.NetworkCallPointsNest
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentFoodsShopPreviewBinding
import com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.adapter.FoodsShopProductAdapter
import com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.modelShopHome.Document
import com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.modelShopHome.Product
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.shophome.adapter.ShopHomeTitleAdapter
import com.teamx.hatlyUser.utils.enum_.Marts
import com.teamx.hatlyUser.utils.snackbar
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

    lateinit var shopCategoryArrayList: ArrayList<Product>
    lateinit var productArrayList: ArrayList<Document>
    lateinit var foodsShopProductAdapter: FoodsShopProductAdapter
    lateinit var shopHomeAdapter: ShopHomeTitleAdapter

    var productLayoutManager2: LinearLayoutManager? = null
    var categoryLayoutManager: GridLayoutManager? = null

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

        when (NetworkCallPointsNest.MART) {
            Marts.HATLY_MART -> {
                Log.d("StoreFragment", "HATLY_MART: back")
            }
            Marts.FOOD -> {
                Log.d("StoreFragment", "FOOD: back")
                mViewModel.foodsShopHome("64db9adcc487c683bbda5c54")
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
            findNavController().navigate(R.id.action_foodsShopHomeFragment_to_reviewFragment)
        }


//        Log.d("itemClasses", "onViewCreated: ${shopShopArrayList.binarySearch("hello")}")


        initializeCategoriesAdapter()
        initializeproductAdapter()

        mViewModel.foodsShopHomeResponse.observe(requireActivity()) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }

                Resource.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    it.data?.let { data ->

                        Picasso.get().load(data.shop.image.secure_url).into(mViewDataBinding.imgShop)

                        mViewDataBinding.textView19.text = try {
                            data.shop.name
                        }catch (e : Exception){
                            "null"
                        }

                        mViewDataBinding.txtDelivery.text = try {
                            "Delivery ${data.shop.delivery.value} ${data.shop.delivery.unit}"
                        }catch (e : Exception){
                            "null"
                        }

                        val rattingSum = data.shop.rattingSum?.`$numberDecimal`?.toFloat()
                        val rattingCount = data.shop.rattingCount?.`$numberDecimal`?.toFloat()!!

                        val rating = rattingSum?.div(rattingCount)

                        mViewDataBinding.txtRating.text = try {
                            rating.toString()
                        }catch (e : Exception){
                            "null"
                        }

                        if (data.shop.isOpen == true){
                            mViewDataBinding.txtIsOpen.text = "Open now"
                        }else{
                            mViewDataBinding.txtIsOpen.text = "Closed"
                        }

                        shopCategoryArrayList.addAll(data.products)
                        shopHomeAdapter.notifyDataSetChanged()
                        clickshopItem(0)
                    }
                }

                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    mViewDataBinding.root.snackbar(it.message!!)
                }
            }
        }


    }

    private fun initializeCategoriesAdapter() {
        shopCategoryArrayList = ArrayList()
        val layoutManager1 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.recCategories.layoutManager = layoutManager1

        shopHomeAdapter = ShopHomeTitleAdapter(shopCategoryArrayList, this)
        mViewDataBinding.recCategories.adapter = shopHomeAdapter
    }

    private fun initializeproductAdapter() {
        productArrayList = ArrayList()
        productLayoutManager2 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.recShopProducts.layoutManager = productLayoutManager2

        foodsShopProductAdapter = FoodsShopProductAdapter(productArrayList)
        mViewDataBinding.recShopProducts.adapter = foodsShopProductAdapter

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
        productArrayList.clear()
        val shopCategoryId = shopCategoryArrayList[position]._id
        Log.d("shopCategoryId", "clickshopItem: $shopCategoryId")
        productArrayList.addAll(shopCategoryArrayList[position].documents)
        foodsShopProductAdapter.notifyDataSetChanged()

//        findNavController().navigate(R.id.action_foodsShopHomeFragment_to_productPreviewFragment)
    }

    override fun clickCategoryItem(position: Int) {

    }

    override fun clickMoreItem(position: Int) {

    }


}