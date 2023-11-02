package com.teamx.hatlyUser.ui.fragments.shophome

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
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentShopHomeBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.shophome.adapter.ShopHomeTitleAdapter
import com.teamx.hatlyUser.ui.fragments.shophome.adapter.SubCategoryProductsAdapter
import com.teamx.hatlyUser.ui.fragments.shophome.model.Document
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ShopHomeFragment : BaseFragment<FragmentShopHomeBinding, ShopHomeViewModel>(),
    HatlyShopInterface {

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
        subCategoryProductsAdapter = SubCategoryProductsAdapter(subCategoryProductsArray, this)
        mViewDataBinding.recShopProducts.adapter = subCategoryProductsAdapter



        itemCategoryTitle = ArrayList()
        val layoutManager1 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.recCategories.layoutManager = layoutManager1
        shopHomeAdapter = ShopHomeTitleAdapter(itemCategoryTitle, this)
        mViewDataBinding.recCategories.adapter = shopHomeAdapter

        if (!mViewModel.storeSubCategoryResponse.hasActiveObservers()) {
            mViewModel.storeSubCategory(storeId, categoryId, "", 1, 10, 0)
//            mViewModel.storeSubCategory("64fb15aec7dd05bb52f7f01c", "64d2437ceccb23edb42b4805", "", 1, 10, 0)
        }

        mViewModel.storeSubCategoryResponse.observe(requireActivity()) {
            when (it.status) {
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

                    mViewDataBinding.root.snackbar(it.message!!)
                    }
                    Log.d("hatlyShopCatAdapter", "ERROR: ${it.message!!}")
                }
            }
        }


    }

    override fun clickshopItem(position: Int) {
        val modelProduct = subCategoryProductsArray[position]
        val bundle = Bundle()
        bundle.putString("_id", modelProduct._id)
        bundle.putString("name", storeName)
        findNavController().navigate(R.id.action_shopHomeFragment_to_productPreviewFragment,bundle)
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


}