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
import com.teamx.hatlyUser.ui.fragments.hatlymart.stores.model.ModelAllStoresItem
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

        when (MART){
            Marts.HATLY_MART -> {
                Log.d("StoreFragment", "HATLY_MART: back")
            }
            Marts.FOOD -> {
                Log.d("StoreFragment", "FOOD: back")
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

        modelAllStoresArraylist = ArrayList()

        val layoutManager1 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.recStores.layoutManager = layoutManager1

        hatlyPopularAdapter = StoresAdapter(modelAllStoresArraylist, this)
        mViewDataBinding.recStores.adapter = hatlyPopularAdapter

        mViewDataBinding.inpSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                if (mViewDataBinding.inpSearch.text.toString().isNotEmpty()) {
                mViewModel.allStores(1, 5, mViewDataBinding.inpSearch.text.toString().trim())
//                }
                return@OnEditorActionListener true
            }
            false
        })

        mViewModel.allStores(1, 5, "")

        if (!mViewModel.allStoresResponse.hasActiveObservers()) {
            mViewModel.allStoresResponse.observe(requireActivity(), Observer {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }
                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            modelAllStoresArraylist.clear()
                            Log.d("allStoresResponse", "onViewCreated: $data")
                            modelAllStoresArraylist.addAll(data)

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
        val hatlyStore = modelAllStoresArraylist[position]
        val bundle = Bundle()
        bundle.putString("_id", hatlyStore._id)
        findNavController().navigate(R.id.action_storesFragment_to_hatlyMartFragment,bundle)
    }

    override fun clickMoreItem(position: Int) {

    }

}