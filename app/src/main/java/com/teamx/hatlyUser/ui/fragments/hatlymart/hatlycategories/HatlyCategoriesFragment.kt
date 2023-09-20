package com.teamx.hatlyUser.ui.fragments.hatlymart.hatlycategories

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentHatlyCategoriesBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.adapter.HatlyShopCatAdapter
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.model.categoryModel.Doc
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HatlyCategoriesFragment :
    BaseFragment<FragmentHatlyCategoriesBinding, HatlyCategoriesViewModel>(), HatlyShopInterface {

    override val layoutId: Int
        get() = R.layout.fragment_hatly_categories
    override val viewModel: Class<HatlyCategoriesViewModel>
        get() = HatlyCategoriesViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var healthDetailCatArraylist: ArrayList<Doc>
    private lateinit var hatlyShopCatAdapter: HatlyShopCatAdapter
    var storeId = ""
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
            storeName = bundle.getString("name", "")
            storeAddress = bundle.getString("address", "")
            mViewDataBinding.textView2.text = storeName
            mViewDataBinding.textViewAddress.text = storeAddress
        }

        mViewDataBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        healthDetailCatArraylist = ArrayList()

        if (storeId.isNotEmpty()) {
            if (!mViewModel.categoryShopResponse.hasActiveObservers()) {
                Log.d("allStoresResponse", "HatlyMartFragment: ")
//                mViewModel.healthDeatil(storeId,1,10,0)
                mViewModel.categoryShop("64db9adcc487c683bbda5c54", 1, 10, 0)
            }
        }

        mViewModel.categoryShopResponse.observe(requireActivity()) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }

                Resource.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    it.data?.let { data ->

                        Log.d("healthDetailCatArra", "onViewCreated: $data")

                        healthDetailCatArraylist.clear()
                        healthDetailCatArraylist.addAll(data.docs)
                        hatlyShopCatAdapter.notifyDataSetChanged()

                    }
                }

                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    mViewDataBinding.root.snackbar(it.message!!)
                    Log.d("hatlyShopCatAdapter", "ERROR: ${it.message!!}")
                }
            }
        }



        val layoutManager = GridLayoutManager(requireActivity(), 4)
        hatlyShopCatAdapter = HatlyShopCatAdapter(healthDetailCatArraylist, this)
        mViewDataBinding.recShopCatMart.layoutManager = layoutManager
        mViewDataBinding.recShopCatMart.adapter = hatlyShopCatAdapter


    }

    override fun clickshopItem(position: Int) {
//        findNavController().navigate(R.id.action_HatlyCategoriesFragment_to_ShopHomeFragment)
    }

    override fun clickCategoryItem(position: Int) {
        val categoryModel = healthDetailCatArraylist[position]
        val bundle = Bundle()
        bundle.putString("_id", storeId)
        bundle.putString("categoryId", categoryModel._id)
        bundle.putString("name", storeName)
        bundle.putString("address", storeAddress)
        findNavController().navigate(R.id.action_HatlyCategoriesFragment_to_ShopHomeFragment,bundle)
    }

    override fun clickMoreItem(position: Int) {

    }


}