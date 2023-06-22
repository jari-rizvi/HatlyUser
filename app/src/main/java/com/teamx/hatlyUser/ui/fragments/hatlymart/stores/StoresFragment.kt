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
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class StoresFragment : BaseFragment<FragmentStoresBinding, StoresViewModel>(), HatlyShopInterface {

    override val layoutId: Int
        get() = R.layout.fragment_stores
    override val viewModel: Class<StoresViewModel>
        get() = StoresViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


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
                            Log.d("allStoresResponse", "onViewCreated: ${data}")
                        }
                    }
                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        mViewDataBinding.root.snackbar(it.message!!)
                    }
                }
            })
        }

        val layoutManager1 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.recStores.layoutManager = layoutManager1

        val itemClasses: ArrayList<String> = ArrayList()

        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")
        itemClasses.add("")

        val hatlyPopularAdapter = StoresAdapter(itemClasses, this)
        mViewDataBinding.recStores.adapter = hatlyPopularAdapter

    }

    override fun clickshopItem(position: Int) {
        findNavController().navigate(R.id.action_storesFragment_to_hatlyMartFragment)
    }

    override fun clickMoreItem(position: Int) {

    }

}