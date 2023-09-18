package com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.MainApplication
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.constants.NetworkCallPointsNest
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentHatlyMartBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.adapter.HatlyPopularAdapter
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.adapter.HatlyShopCatAdapter
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.model.categoryModel.Doc
import com.teamx.hatlyUser.utils.enum_.Marts
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HatlyMartFragment : BaseFragment<FragmentHatlyMartBinding, HatlyMartViewModel>(),
    HatlyShopInterface {

    override val layoutId: Int
        get() = R.layout.fragment_hatly_mart
    override val viewModel: Class<HatlyMartViewModel>
        get() = HatlyMartViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var healthDetailCatArraylist: ArrayList<Doc>
    private lateinit var healthDetailPopularArraylist: ArrayList<com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.model.popularproductmodel.Doc>

    private lateinit var hatlyShopCatAdapter: HatlyShopCatAdapter
    private lateinit var hatlyPopularAdapter: HatlyPopularAdapter

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

        mViewDataBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        healthDetailCatArraylist = ArrayList()
        healthDetailPopularArraylist = ArrayList()

        val bundle = arguments
        if (bundle != null) {
            val parcel = bundle.getBoolean("parcel", false)
            storeId = bundle.getString("_id", "")
            storeName = bundle.getString("name", "")
            storeAddress = bundle.getString("address", "")
            mViewDataBinding.textView2.text = try {
                storeName
            }catch (e : Exception){
                ""
            }
            mViewDataBinding.textViewAddress.text = try {
                storeAddress
            }catch (e : Exception){
                ""
            }
            when {
                parcel -> {
                    mViewDataBinding.constraintLayout2.visibility = View.VISIBLE
                }

                else -> {
                    mViewDataBinding.constraintLayout2.visibility = View.GONE
                }
            }
        }

        mViewDataBinding.txtParcel.setOnClickListener {
            findNavController().navigate(R.id.action_hatlyMartFragment_to_specialOrderFragment)
        }

        when (NetworkCallPointsNest.MART) {
            Marts.HATLY_MART -> {
                Log.d("StoreFragment", "HATLY_MART: back")
                mViewDataBinding.txtShopCatTitle.text = "Shop by categories:"
                mViewDataBinding.txtPopular.text = "Popular Items:"
            }

            Marts.FOOD -> {
                Log.d("StoreFragment", "FOOD: back")
            }

            Marts.GROCERY -> {
                Log.d("StoreFragment", "GROCERY: back")
                mViewDataBinding.txtShopCatTitle.text = "Shop by categories:"
                mViewDataBinding.txtPopular.text = "Popular Items:"
            }

            Marts.HEALTH_BEAUTY -> {
                Log.d("StoreFragment", "HEALTH_BEAUTY: back")
                mViewDataBinding.txtShopCatTitle.text = "Shop by categories:"
                mViewDataBinding.txtPopular.text = "Trending Now"
            }

            Marts.HOME_BUSINESS -> {
                Log.d("StoreFragment", "HOME_BUSINESS: back")
            }
        }


//        val layoutManager1 = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
//        mViewDataBinding.recBasedMart.layoutManager = layoutManager1



        if (storeId.isNotEmpty()) {
            if (!mViewModel.categoryShopResponse.hasActiveObservers()) {
                Log.d("allStoresResponse", "HatlyMartFragment: ")
//                mViewModel.healthDeatil(storeId,1,10,0)
                mViewModel.categoryShop("64db9adcc487c683bbda5c54", 1, 10, 0)
            }
        }

//        if (!mViewModel.healthDetailResponse.hasActiveObservers()) {
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
                        healthDetailCatArraylist.add(
                            Doc(
                                0,
                                "",
                                "",
                                "More categories",
                                "",
                                null,
                                true
                            )
                        )
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
//        }

//        mViewModel.popularProductsShop(storeId)
        mViewModel.popularProductsShop("64db9adcc487c683bbda5c54")

        mViewModel.popularProductsResponse.observe(requireActivity(), Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }
                Resource.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    it.data?.let { data ->
                        healthDetailPopularArraylist.clear()
                        healthDetailPopularArraylist.addAll(data.docs)
                        hatlyPopularAdapter.notifyDataSetChanged()
                    }
                }
                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    mViewDataBinding.root.snackbar(it.message!!)
                    Log.d("hatlyShopCatAdapter", "ERROR: ${it.message!!}")
                }
            }
        })


        // set the adapter

        // set the adapter
        val layoutManager = GridLayoutManager(requireActivity(), 4)
        hatlyShopCatAdapter = HatlyShopCatAdapter(healthDetailCatArraylist, this)
        mViewDataBinding.recShopCatMart.layoutManager = layoutManager
        mViewDataBinding.recShopCatMart.adapter = hatlyShopCatAdapter
//        mViewDataBinding.recBasedMart.adapter = hatlyShopCatAdapter


        val productLayoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        hatlyPopularAdapter = HatlyPopularAdapter(healthDetailPopularArraylist, this)
        mViewDataBinding.recPopular.layoutManager = productLayoutManager
        mViewDataBinding.recPopular.adapter = hatlyPopularAdapter


    }

    override fun clickshopItem(position: Int) {
        findNavController().navigate(R.id.action_hatlyMartFragment_to_productPreviewFragment)
        Toast.makeText(MainApplication.context, "Shop", Toast.LENGTH_SHORT).show()
    }

    override fun clickCategoryItem(position: Int) {
        val categoryModel = healthDetailCatArraylist[position]
        val bundle = Bundle()
        bundle.putString("_id", storeId)
        bundle.putString("categoryId", categoryModel._id)
        bundle.putString("name", storeName)
        bundle.putString("address", storeAddress)
        findNavController().navigate(R.id.action_hatlyMartFragment_to_ShopHomeFragment,bundle)
        Toast.makeText(MainApplication.context, "Category", Toast.LENGTH_SHORT).show()
    }

    override fun clickMoreItem(position: Int) {

        val bundle = Bundle()
        bundle.putString("_id", storeId)
        bundle.putString("name", storeName)
        bundle.putString("address", storeAddress)
        findNavController().navigate(R.id.action_hatlyMartFragment_to_HatlyCategoriesFragment,bundle)
        Toast.makeText(MainApplication.context, "More", Toast.LENGTH_SHORT).show()
    }


}