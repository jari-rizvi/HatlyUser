package com.teamx.hatlyUser.ui.fragments.shophome

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentShopHomeBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.adapter.AddToCartInterface
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.shophome.adapter.ShopHomeTitleAdapter
import com.teamx.hatlyUser.ui.fragments.shophome.adapter.SubCategoryProductsAdapter
import com.teamx.hatlyUser.ui.fragments.shophome.model.Document
import com.teamx.hatlyUser.utils.DialogHelperClass
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import java.util.Stack


@AndroidEntryPoint
class ShopHomeFragment : BaseFragment<FragmentShopHomeBinding, ShopHomeViewModel>(),
    HatlyShopInterface, AddToCartInterface, DialogHelperClass.Companion.MultiProduct {

    override val layoutId: Int
        get() = R.layout.fragment_shop_home
    override val viewModel: Class<ShopHomeViewModel>
        get() = ShopHomeViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var shopHomeAdapter: ShopHomeTitleAdapter
    private lateinit var subCategoryProductsAdapter: SubCategoryProductsAdapter
    private lateinit var subCategoryProductsArray: ArrayList<Document>
    private lateinit var itemCategoryTitle: ArrayList<com.teamx.hatlyUser.ui.fragments.shophome.model.Doc>

    var storeId = ""
    var categoryId = ""
    var storeName = ""
    var storeAddress = ""

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

        val bundle = arguments
        if (bundle != null) {
            storeId = bundle.getString("_id", "")
            categoryId = bundle.getString("categoryId", "")
            storeName = bundle.getString("name", "")
            storeAddress = bundle.getString("address", "")
            mViewDataBinding.textView2.text = try {
                storeName
            } catch (e: Exception) {
                ""
            }
            mViewDataBinding.textViewAddress.text = try {
                storeAddress
            } catch (e: Exception) {
                ""
            }
        }


        subCategoryProductsArray = ArrayList()
        val layoutManager = GridLayoutManager(requireActivity(), 2)
        mViewDataBinding.recShopProducts.layoutManager = layoutManager
        subCategoryProductsAdapter = SubCategoryProductsAdapter(subCategoryProductsArray, this,this)
        mViewDataBinding.recShopProducts.adapter = subCategoryProductsAdapter



        itemCategoryTitle = ArrayList()
        val layoutManager1 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.recCategories.layoutManager = layoutManager1
        shopHomeAdapter = ShopHomeTitleAdapter(itemCategoryTitle, this)
        mViewDataBinding.recCategories.adapter = shopHomeAdapter

        mViewDataBinding.inpSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Log.d("inpSearch", "onViewCreated: fdfdf")
                performSearch()
            }
            true
        }

        performSearch()

        mViewModel.storeSubCategoryResponse.observe(requireActivity()) {
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
                        itemCategoryTitle.clear()
                        subCategoryProductsArray.clear()
                        data.docs?.forEach {
                            itemCategoryTitle.add(it)
                        }
                        Log.d("performSearch", "storeSubCategoryResponse: ${data.docs}")
                        if (data.docs?.isNotEmpty() == true) {
                            clickCategoryItem(0)
//                            itemCategoryTitle[0].isSelected = true
//                            subCategoryProductsArray.addAll(itemCategoryTitle[0].documents)
                        }

                        shopHomeAdapter.notifyDataSetChanged()
                        subCategoryProductsAdapter.notifyDataSetChanged()
                    }
                }

                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    if (isAdded) {

                        mViewDataBinding.mainLayout.snackbar(it.message!!)
                    }
                    Log.d("hatlyShopCatAdapter", "ERROR: ${it.message!!}")
                }
            }
        }

        if (!mViewModel.addToCartResponse.hasActiveObservers()) {
            mViewModel.addToCartResponse.observe(requireActivity()) {
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
                                if (isAddToCartRecommend) {
                                    subCategoryProductsArray[addToCartPosition].cartItemId = data.cartItemId
                                    subCategoryProductsArray[addToCartPosition].cartExistence = true
                                    subCategoryProductsArray[addToCartPosition].cartQuantity = 1
                                    subCategoryProductsAdapter.notifyItemChanged(addToCartPosition)
                                } else {
                                    if (isAdded) {
                                        mViewDataBinding.mainLayout.snackbar("Added")
                                    }
                                }
                            }
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        if (isAdded) {

                            if (it.message == "Can not add products from multiple shops") {
                                DialogHelperClass.MultiProductDialog(requireContext(), this)
                                Log.d("addToCartResponse", "addToCart: ${it.message!!}")
                            }
                        }
                    }
                }
            }
        }

        if (!mViewModel.emptyCartResponse.hasActiveObservers()) {
            mViewModel.emptyCartResponse.observe(requireActivity()) {
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
                            mViewDataBinding.mainLayout.snackbar("Cart is empty now you can add product")
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        if (isAdded) {

                            mViewDataBinding.mainLayout.snackbar("${it.message!!}")
                            Log.d("addToCartResponse", "addToCart: ${it.message!!}")
                        }
                    }
                }
            }
        }


    }

    private fun performSearch() {
        mViewModel.storeSubCategory(
            storeId,
            categoryId,
            mViewDataBinding.inpSearch.text.toString().trim(),
            1,
            10,
            0
        )
    }

    override fun clickshopItem(position: Int) {
        val modelProduct = subCategoryProductsArray[position]
        val bundle = Bundle()
        bundle.putString("_id", modelProduct._id)
        bundle.putString("name", storeName)
        findNavController().navigate(R.id.action_shopHomeFragment_to_productPreviewFragment, bundle)
    }

    override fun clickCategoryItem(position: Int) {
        itemCategoryTitle.forEach {
            it.isSelected = false
        }
        val categoryitem = itemCategoryTitle[position]
        subCategoryProductsArray.clear()
        subCategoryProductsArray.addAll(categoryitem.documents)
        itemCategoryTitle[position].isSelected = true
        shopHomeAdapter.notifyDataSetChanged()
        subCategoryProductsAdapter.notifyDataSetChanged()
    }

    override fun clickMoreItem(position: Int) {

    }

    private var isAddToCartRecommend = false
    private var addToCartPosition = -1

    override fun addProduct(position: Int) {

        val prodModel = subCategoryProductsArray[position]

        if (prodModel.productType == "simple") {
            val params = JsonObject()
            try {
                params.addProperty("id", prodModel._id)
                params.addProperty("quantity", 1)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            isAddToCartRecommend = true
            addToCartPosition = position
            mViewModel.addToCart(params)
        }else{
            val bundle = Bundle()
            bundle.putString("_id", prodModel._id)
            bundle.putString("name", storeName)
            findNavController().navigate(R.id.action_shopHomeFragment_to_productPreviewFragment, bundle)
        }
    }

    private val debounceDelayMillis = 1000 // Set your desired debounce delay in milliseconds
    private val handlerQty = Handler(Looper.getMainLooper())
    private val actionStack = Stack<Int>()

    override fun updateQuantity(position: Int, quantity: Int) {


        if (quantity > 0) {
            handlerQty.removeCallbacksAndMessages(null)
            if (!actionStack.contains(position)) {
                actionStack.push(position)
            }
            subCategoryProductsArray[position].cartQuantity = quantity
            subCategoryProductsAdapter.notifyItemChanged(position)

            updateQtyResponse()

        }
    }

    private fun updateQtyResponse() {

        val params = JsonObject()

        handlerQty.postDelayed({
            if (actionStack.isNotEmpty()) {
                val cartmodel = subCategoryProductsArray[actionStack.pop()]
                params.addProperty("id", cartmodel.cartItemId)
                params.addProperty("quantity", cartmodel.cartQuantity)
                mViewModel.updateCartItem(params)

                mViewModel.updateCartItemResponse.observe(requireActivity()) {
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

                                if (actionStack.isNotEmpty()) {
                                    val cartmodel1 = subCategoryProductsArray[actionStack.pop()]
                                    params.addProperty("id", cartmodel1.cartItemId)
                                    params.addProperty("quantity", cartmodel1.cartQuantity)
                                    mViewModel.updateCartItem(params)
                                } else {
//                                    layoutUpdate(data)
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

        }, debounceDelayMillis.toLong())
    }

    override fun prodRemove() {
        mViewModel.emptyCart()
    }


}