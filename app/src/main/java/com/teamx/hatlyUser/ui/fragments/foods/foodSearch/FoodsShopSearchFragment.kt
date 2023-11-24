package com.teamx.hatlyUser.ui.fragments.foods.foodSearch

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.constants.NetworkCallPointsNest
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentFoodsShopSearchBinding
import com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.FoodsShopPreviewViewModel
import com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.adapter.FoodsShopProductAdapter
import com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.modelShopHome.Document
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.utils.enum_.Marts
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FoodsShopSearchFragment :
    BaseFragment<FragmentFoodsShopSearchBinding, FoodsShopPreviewViewModel>(), HatlyShopInterface {

    override val layoutId: Int
        get() = R.layout.fragment_foods_shop_search
    override val viewModel: Class<FoodsShopPreviewViewModel>
        get() = FoodsShopPreviewViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    var shopId = ""
    private lateinit var productArrayList: ArrayList<Document>
    private lateinit var filterProdArrayList: ArrayList<Document>
    private var productLayoutManager2: LinearLayoutManager? = null
    private var foodsShopProductAdapter: FoodsShopProductAdapter? = null

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

        productArrayList = ArrayList()
        filterProdArrayList = ArrayList()

        initialize(filterProdArrayList)

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

        mViewModel.foodsShopHomeResponse.observe(requireActivity())
        {
            when (it.status) {
                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }

                Resource.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    it.data?.let { data ->

                        shopId = data.shop._id

                        productArrayList.clear()
                        filterProdArrayList.clear()
                        data.products.forEach { documents ->
                            documents.documents.forEach {
                                productArrayList.add(it)
                                filterProdArrayList.add(it)
                            }
                        }

                        foodsShopProductAdapter!!.notifyDataSetChanged()


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

        mViewDataBinding.inpSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s!!.isNotEmpty()) {
                    filterProdArrayList = productArrayList.filter { it.name.lowercase().contains(s.toString().lowercase()) } as ArrayList<Document>
                } else {
                    filterProdArrayList.clear()
                    filterProdArrayList.addAll(productArrayList)
                }
                initialize(filterProdArrayList)
            }

        })

    }

    fun initialize(productArrayList1: ArrayList<Document>) {
        productLayoutManager2 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.recShopProducts.layoutManager = productLayoutManager2
        foodsShopProductAdapter = FoodsShopProductAdapter(productArrayList1, this)
        mViewDataBinding.recShopProducts.adapter = foodsShopProductAdapter
    }

    override fun clickshopItem(position: Int) {
        val modelProduct = filterProdArrayList[position]
        val bundle = Bundle()
        bundle.putString("_id", modelProduct._id)
        bundle.putString("name", modelProduct.name)
        findNavController().navigate(
            R.id.action_foodSearchFragment_to_productPreviewFragment,
            bundle
        )
    }

    override fun clickCategoryItem(position: Int) {

    }

    override fun clickMoreItem(position: Int) {

    }


}