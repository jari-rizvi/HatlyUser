package com.teamx.hatlyUser.ui.fragments.hatlymart.stores

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.constants.NetworkCallPointsNest.Companion.MART
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentStoresBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.hatlymart.stores.adapter.StoresAdapter
import com.teamx.hatlyUser.ui.fragments.hatlymart.stores.model.Doc
import com.teamx.hatlyUser.ui.fragments.hatlymart.stores.model.ModelAllStores
import com.teamx.hatlyUser.utils.enum_.Marts
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

    private lateinit var modelAllStoresArraylist: ArrayList<Doc>

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

        when (MART) {
            Marts.HATLY_MART -> {
                Log.d("StoreFragment", "HATLY_MART: back")
            }
            Marts.FOOD -> {
                Log.d("StoreFragment", "FOOD: back")
            }
            Marts.GROCERY -> {
                Log.d("StoreFragment", "GROCERY: back")
                if (!mViewModel.allHealthAndBeautyStoresResponse.hasActiveObservers()) {
                    mViewModel.allHealthAndBeautyStores(1, 10, "",0,"grocery")
                    Log.d("allStoresResponse", "allStores: ")
                }
            }
            Marts.HEALTH_BEAUTY -> {
                Log.d("StoreFragment", "HEALTH_BEAUTY: back")
            }
            Marts.HOME_BUSINESS -> {
                Log.d("StoreFragment", "HOME_BUSINESS: back")
            }
        }

        modelAllStoresArraylist = ArrayList()

        val layoutManager1 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.recStores.layoutManager = layoutManager1

        hatlyPopularAdapter = StoresAdapter(modelAllStoresArraylist, this)
        mViewDataBinding.recStores.adapter = hatlyPopularAdapter


        mViewDataBinding.inpSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                when (MART) {
                    Marts.HATLY_MART -> {

                    }
                    Marts.FOOD -> {

                    }
                    Marts.GROCERY -> {
                        mViewModel.allHealthAndBeautyStores(1, 10, mViewDataBinding.inpSearch.text.toString().trim(),0,"grocery")
                    }
                    Marts.HEALTH_BEAUTY -> {
                    }
                    Marts.HOME_BUSINESS -> {

                    }
                }
            }
            true
        }

//        if (!mViewModel.allStoresResponse.hasActiveObservers()) {
        mViewModel.allHealthAndBeautyStoresResponse.observe(requireActivity(), Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }
                Resource.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    it.data?.let { data ->
                        modelAllStoresArraylist.clear()
                        Log.d("allStoresResponse", "onViewCreated: $data")
                        modelAllStoresArraylist.addAll(data.docs)

                        hatlyPopularAdapter.notifyDataSetChanged()
                    }
                }
                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    Log.d("allStoresResponse", "ERROR: $it.message!!")
                    mViewDataBinding.root.snackbar(it.message!!)
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
        bundle.putString("address", hatlyStore.address.googleMapAddress)
        findNavController().navigate(R.id.action_storesFragment_to_hatlyMartFragment, bundle)
    }

    override fun clickCategoryItem(position: Int) {

    }

    override fun clickMoreItem(position: Int) {

    }

}