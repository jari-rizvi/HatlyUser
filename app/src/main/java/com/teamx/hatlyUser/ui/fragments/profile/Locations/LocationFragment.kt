package com.teamx.hatlyUser.ui.fragments.profile.Locations

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.AbsListView
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentLocationBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.location.map.models.CreateAddressModel
import com.teamx.hatlyUser.ui.fragments.location.map.models.CreateAddressModelItem
import com.teamx.hatlyUser.ui.fragments.profile.Locations.adapter.LocationsListAdapter
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LocationFragment : BaseFragment<FragmentLocationBinding, LocationViewModel>(),
    HatlyShopInterface {

    override val layoutId: Int
        get() = R.layout.fragment_location
    override val viewModel: Class<LocationViewModel>
        get() = LocationViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var getAddressArray: ArrayList<CreateAddressModelItem>
    private lateinit var locationsListAdapter: LocationsListAdapter

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

        mViewDataBinding.txtAddLocation.setOnClickListener {
            val locationModel = CreateAddressModelItem("", "", "", 0, "", "", 0.0, 0.0, "", "Add")
            sharedViewModel.setlocationmodel(locationModel)
            findNavController().navigate(R.id.action_locationFragment_to_mapFragment)
        }

        getAddressArray = ArrayList()

        mViewDataBinding.recLocations.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        locationsListAdapter = LocationsListAdapter(getAddressArray, this)
        mViewDataBinding.recLocations.adapter = locationsListAdapter


        mViewModel.getAlAddress()

        if (!mViewModel.getAlAddressResponse.hasActiveObservers()) {
            mViewModel.getAlAddressResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->

                            data[0].isSelected = true

                            getAddressArray.addAll(data)
                            locationsListAdapter.notifyDataSetChanged()

                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        mViewDataBinding.root.snackbar(it.message!!)
                    }
                }
            }
        }

        if (!mViewModel.setDefaultAddressResponse.hasActiveObservers()) {
            mViewModel.setDefaultAddressResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            if (data.success) {
                                mViewDataBinding.root.snackbar(data.message)
                            }
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        mViewDataBinding.root.snackbar(it.message!!)
                    }
                }
            }
        }
    }


    override fun clickshopItem(selectPosition: Int) {
        getAddressArray.forEach { it.isSelected = false }
        getAddressArray[selectPosition].isSelected = true
        locationsListAdapter.notifyDataSetChanged()
        mViewModel.setDefaultAddress(getAddressArray[selectPosition]._id)
    }

    override fun clickCategoryItem(updatePosition: Int) {
        val locationModel = getAddressArray[updatePosition]
        locationModel.isAction = "Update"
        sharedViewModel.setlocationmodel(locationModel)
        findNavController().navigate(R.id.action_locationFragment_to_mapFragment)
    }

    override fun clickMoreItem(position: Int) {

    }


}