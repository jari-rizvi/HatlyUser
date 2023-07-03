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
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.model.Categore
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.model.ModelHealthDetail
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.model.PopularProduct
import com.teamx.hatlyUser.ui.fragments.hatlymart.stores.adapter.StoresAdapter
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

    private lateinit var healthDetailCatArraylist: ArrayList<Categore>
    private lateinit var healthDetailPopularArraylist: ArrayList<PopularProduct>

    private lateinit var hatlyShopCatAdapter: HatlyShopCatAdapter
    private lateinit var hatlyPopularAdapter: HatlyPopularAdapter

    var storeId = ""

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

        val layoutManager = GridLayoutManager(requireActivity(), 4)
        mViewDataBinding.recShopCatMart.layoutManager = layoutManager
        val layoutManager1 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        val layoutManager2 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.recBasedMart.layoutManager = layoutManager1
        mViewDataBinding.recPopular.layoutManager = layoutManager2

        if (storeId.isNotEmpty()) {
            if (!mViewModel.healthDetailResponse.hasActiveObservers()) {
                Log.d("allStoresResponse", "HatlyMartFragment: ")
                mViewModel.healthDeatil(storeId)
            }
        }

//        if (!mViewModel.healthDetailResponse.hasActiveObservers()) {
        mViewModel.healthDetailResponse.observe(requireActivity(), Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }
                Resource.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    it.data?.let { data ->

                        healthDetailCatArraylist.clear()
                        healthDetailPopularArraylist.clear()

                        healthDetailCatArraylist.addAll(data.categores)
                        healthDetailPopularArraylist.addAll(data.popularProduct)

                        hatlyShopCatAdapter.notifyDataSetChanged()
                        hatlyPopularAdapter.notifyDataSetChanged()
                    }
                }
                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    mViewDataBinding.root.snackbar(it.message!!)
                }
            }
        })
//        }

        hatlyShopCatAdapter = HatlyShopCatAdapter(healthDetailCatArraylist, this)
        hatlyPopularAdapter = HatlyPopularAdapter(healthDetailPopularArraylist, this)

        // set the adapter

        // set the adapter
        mViewDataBinding.recShopCatMart.adapter = hatlyShopCatAdapter
        mViewDataBinding.recBasedMart.adapter = hatlyShopCatAdapter
        mViewDataBinding.recPopular.adapter = hatlyPopularAdapter

    }

    override fun clickshopItem(position: Int) {
        findNavController().navigate(R.id.action_hatlyMartFragment_to_ShopHomeFragment)
        Toast.makeText(MainApplication.context, "Shop", Toast.LENGTH_SHORT).show()
    }

    override fun clickMoreItem(position: Int) {
        findNavController().navigate(R.id.action_hatlyMartFragment_to_HatlyCategoriesFragment)
        Toast.makeText(MainApplication.context, "More", Toast.LENGTH_SHORT).show()
    }


}