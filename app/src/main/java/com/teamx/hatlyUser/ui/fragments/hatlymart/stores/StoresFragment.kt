package com.teamx.hatlyUser.ui.fragments.hatlymart.stores

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentStoresBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.hatlymart.stores.adapter.StoresAdapter
import com.teamx.hatlyUser.ui.fragments.hatlymart.stores.model.ModelAllStores
import com.teamx.hatlyUser.ui.fragments.hatlymart.stores.model.ModelAllStoresItem
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StoresFragment : BaseFragment<FragmentStoresBinding, StoresViewModel>(), HatlyShopInterface {

    override val layoutId: Int
        get() = R.layout.fragment_stores
    override val viewModel: Class<StoresViewModel>
        get() = StoresViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

//    val itemClasses: ArrayList<String> = ArrayList()

    private lateinit var modelAllStoresArraylist: ArrayList<ModelAllStoresItem>

    private lateinit var hatlyPopularAdapter: StoresAdapter

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

        modelAllStoresArraylist = ArrayList()

        val layoutManager1 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.recStores.layoutManager = layoutManager1

        hatlyPopularAdapter = StoresAdapter(modelAllStoresArraylist, this)
        mViewDataBinding.recStores.adapter = hatlyPopularAdapter


        mViewModel.allStores(1,5)

        if (!mViewModel.allStoresResponse.hasActiveObservers()) {
            mViewModel.allStoresResponse.observe(requireActivity(), Observer {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }
                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->

//                            data.forEachIndexed { index, element ->
//
//                                val jsonObject = element.asJsonObject
//                                val id = jsonObject["_id"].asString
//                                val name = jsonObject["name"].asString
//                                val imageUrl = jsonObject["image"].asJsonObject["secure_url"].asString
//                                val totalReviews = jsonObject["totalReviews"].asInt
//                                val rating = jsonObject["ratting"].asString
//                                val deliveryObject = jsonObject["delivery"].asJsonObject
//                                val deliveryValue = deliveryObject["value"].asInt
//                                val deliveryUnit = deliveryObject["unit"].asString
//                                val shopAddressObject = jsonObject["shopAddress"].asJsonObject
//                                val country = shopAddressObject["country"].asString
//                                val state = shopAddressObject["state"].asString
//                                val city = shopAddressObject["city"].asString
//                                val googleMapAddress =
//                                    shopAddressObject["googleMapAddress"].asString
//                                val phoneCode = shopAddressObject["phoneCode"].asString
//                                val store = ModelAllStores()
//
//                                store[index]._id = id
//                                store[index].name = name
//                                store[index].ratting = rating
//                                store[index].totalReviews = totalReviews
//                                store[index].image?.secure_url = imageUrl
//                                store[index].image?.public_id = p
//
//                                store.set_id(id)
//                                store.setName(name)
//                                store.setImage(imageUrl)
//                                store.setTotalReviews(totalReviews)
//                                store.setRating(rating)
//                                store.setDeliveryValue(deliveryValue)
//                                store.setDeliveryUnit(deliveryUnit)
//                                store.setCountry(country)
//                                store.setState(state)
//                                store.setCity(city)
//                                store.setGoogleMapAddress(googleMapAddress)
//                                store.setPhoneCode(phoneCode)
//                                storeList.add(store)
//                            }

//                            modelAllStoresArraylist.addAll(modelAllStores)
//                            Log.d("allStoresResponse", "onViewCreated: ${modelAllStores}")

                            hatlyPopularAdapter.notifyDataSetChanged()
                        }
                    }
                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        mViewDataBinding.root.snackbar(it.message!!)
                    }
                }
            })
        }
    }

    override fun clickshopItem(position: Int) {
        findNavController().navigate(R.id.action_storesFragment_to_hatlyMartFragment)
    }

    override fun clickMoreItem(position: Int) {

    }

}