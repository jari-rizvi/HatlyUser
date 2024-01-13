package com.teamx.hatlyUser.ui.fragments.hatlymart.stores

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.constants.NetworkCallPointsNest.Companion.MART
import com.teamx.hatlyUser.data.remote.Resource

import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.hatlymart.stores.adapter.StoresAdapter
import com.teamx.hatlyUser.ui.fragments.hatlymart.stores.bottomsheet.BottomSheetRatDelListener
import com.teamx.hatlyUser.ui.fragments.hatlymart.stores.bottomsheet.BottomSheetRatingDeliveryFragment
import com.teamx.hatlyUser.ui.fragments.hatlymart.stores.model.Doc
import com.teamx.hatlyUser.utils.enum_.Marts
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoresFragment :
    BaseFragment<com.teamx.hatlyUser.databinding.FragmentStoresBinding, StoresViewModel>(),
    HatlyShopInterface,
    BottomSheetRatDelListener {

    override val layoutId: Int
        get() = R.layout.fragment_stores
    override val viewModel: Class<StoresViewModel>
        get() = StoresViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

//    val itemClasses: ArrayList<String> = ArrayList()

    private lateinit var modelAllStoresArraylist: ArrayList<Doc>

    private lateinit var hatlyPopularAdapter: StoresAdapter

    private lateinit var bottomSheetAddSearchFragment: BottomSheetRatingDeliveryFragment

    private var isDelivery = true

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


        sharedViewModel.userData.observe(requireActivity()) {
            Log.d("userDatavalue", "${it.location?.address}")
        }

        mViewDataBinding.textViewAddress.text = try {
            extractShortAddress(sharedViewModel.userData.value?.location?.address)

        } catch (e: Exception) {
            ""
        }


        if (!mViewModel.allHealthAndBeautyStoresResponse.hasActiveObservers()) {
            hitApi(searchshop, deliverTime, rating)
        }

//        when (MART) {
//            Marts.HATLY_MART -> {
//                Log.d("StoreFragment", "HATLY_MART: back")
//            }
//
//            Marts.FOOD -> {
//                Log.d("StoreFragment", "FOOD: back")
//            }
//
//            Marts.GROCERY -> {
//                Log.d("StoreFragment", "GROCERY: back")
//                if (!mViewModel.allHealthAndBeautyStoresResponse.hasActiveObservers()) {
//                    mViewModel.allHealthAndBeautyStores(
//                        1,
//                        10,
//                        "",
//                        0,
//                        "grocery",
//                        deliverTime,
//                        rating
//                    )
//                    Log.d("allStoresResponse", "allStores: ")
//                }
//            }
//
//            Marts.HEALTH_BEAUTY -> {
//                Log.d("StoreFragment", "HEALTH_BEAUTY: back")
//                if (!mViewModel.allHealthAndBeautyStoresResponse.hasActiveObservers()) {
//                    mViewModel.allHealthAndBeautyStores(1, 10, "", 0, "health", deliverTime, rating)
//                    Log.d("allStoresResponse", "allStores: ")
//                }
//            }
//
//            Marts.HOME_BUSINESS -> {
//                Log.d("StoreFragment", "HOME_BUSINESS: back")
//                if (!mViewModel.allHealthAndBeautyStoresResponse.hasActiveObservers()) {
//                    mViewModel.allHealthAndBeautyStores(
//                        1,
//                        10,
//                        "",
//                        0,
//                        "home based",
//                        deliverTime,
//                        rating
//                    )
//                    Log.d("allStoresResponse", "allStores: ")
//                }
//            }
//        }

        modelAllStoresArraylist = ArrayList()

        val layoutManager1 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.recStores.layoutManager = layoutManager1

        hatlyPopularAdapter = StoresAdapter(modelAllStoresArraylist, this)
        mViewDataBinding.recStores.adapter = hatlyPopularAdapter


        mViewDataBinding.inpSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                when (MART) {
//                    Marts.HATLY_MART -> {
//
//                    }
//
//                    Marts.FOOD -> {
//
//                    }
//
//                    Marts.GROCERY -> {
//                        mViewModel.allHealthAndBeautyStores(
//                            1,
//                            10,
//                            mViewDataBinding.inpSearch.text.toString().trim(),
//                            0,
//                            "grocery",
//                            deliverTime,
//                            rating
//                        )
//                    }
//
//                    Marts.HEALTH_BEAUTY -> {
//                        mViewModel.allHealthAndBeautyStores(
//                            1,
//                            10,
//                            mViewDataBinding.inpSearch.text.toString().trim(),
//                            0,
//                            "health",
//                            deliverTime,
//                            rating
//                        )
//                    }
//
//                    Marts.HOME_BUSINESS -> {
//                        mViewModel.allHealthAndBeautyStores(
//                            1,
//                            10,
//                            mViewDataBinding.inpSearch.text.toString().trim(),
//                            0,
//                            "home based",
//                            deliverTime,
//                            rating
//                        )
//                    }
//                }
                searchshop = mViewDataBinding.inpSearch.text.toString().trim()
                hitApi(searchshop, deliverTime, rating)
            }
            true
        }

//        if (!mViewModel.allStoresResponse.hasActiveObservers()) {
        mViewModel.allHealthAndBeautyStoresResponse.observe(requireActivity(), Observer {
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
                        modelAllStoresArraylist.clear()
                        data.docs?.let { it1 -> modelAllStoresArraylist.addAll(it1) }

                        hatlyPopularAdapter.notifyDataSetChanged()
                    }
                }

                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    Log.d("allStoresResponse", "ERROR: $it.message!!")
                    if (isAdded) {

                        mViewDataBinding.root.snackbar(it.message!!)
                    }
                }
            }
        })
//        }
    }

    override fun clickshopItem(position: Int) {
        val hatlyStore = modelAllStoresArraylist[position]
        val bundle = Bundle()
        bundle.putString("_id", hatlyStore._id)
        bundle.putString("name", hatlyStore.name)
//        bundle.putString("address", hatlyStore.address.googleMapAddress)
        val shortAddress = extractShortAddress(sharedViewModel.userData.value?.location?.address)
        bundle.putString("address", shortAddress)
        findNavController().navigate(R.id.action_storesFragment_to_hatlyMartFragment, bundle)
    }

    override fun clickCategoryItem(position: Int) {

    }

    override fun clickMoreItem(position: Int) {

    }

    private fun extractShortAddress(fullAddress: String?): String? {
        val addressParts = fullAddress?.split(", ")
        val shortAddress = addressParts?.get(0)
        return shortAddress
    }

    private var deliverTime: Int? = null
    private var rating: Int? = null
    private var searchshop = ""
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

        hitApi(searchshop, deliverTime, rating)

    }

    private fun hitApi(search: String, deliveryTime: Int?, rating: Int?) {
        when (MART) {
            Marts.HATLY_MART -> {
            }

            Marts.FOOD -> {
            }

            Marts.GROCERY -> {
                mViewModel.allHealthAndBeautyStores(
                    1,
                    10,
                    search,
                    0,
                    "grocery",
                    deliveryTime,
                    rating
                )
            }

            Marts.HEALTH_BEAUTY -> {
                mViewModel.allHealthAndBeautyStores(1, 10, "", 0, "health", deliveryTime, rating)
            }

            Marts.HOME_BUSINESS -> {
                mViewModel.allHealthAndBeautyStores(
                    1,
                    10,
                    "",
                    0,
                    "home based",
                    deliveryTime,
                    rating
                )
            }
        }
    }

}