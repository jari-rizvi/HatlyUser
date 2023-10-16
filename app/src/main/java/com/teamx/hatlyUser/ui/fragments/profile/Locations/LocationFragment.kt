package com.teamx.hatlyUser.ui.fragments.profile.Locations

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.AbsListView
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


    private lateinit var getAddressArray : ArrayList<CreateAddressModelItem>
    private lateinit var locationsListAdapter : LocationsListAdapter

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
            val locationModel = CreateAddressModelItem("","","",0,"","",0.0,0.0,"","Add")
            sharedViewModel.setlocationmodel(locationModel)
            findNavController().navigate(R.id.action_locationFragment_to_mapFragment)
        }

        getAddressArray = ArrayList()

        mViewDataBinding.recLocations.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
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

//        mViewDataBinding.recLocations.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
//                    isScrolling = true
//                }
//            }
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//                currentItems = layoutManager1.childCount
//                totalItems = layoutManager1.itemCount
//                scrollOutItems = layoutManager1.findFirstVisibleItemPosition()
//
//                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
//                    isScrolling = false
////                    fetchData()
//                }
//            }
//        })

    }

//    private fun fetchData() {
//        mViewDataBinding.spinKit.visibility = View.VISIBLE
//        Handler(Looper.getMainLooper()).postDelayed({
//            for (i in 1..5) {
//                itemClasses.add("")
//                itemClasses.add("")
//                itemClasses.add("")
//                itemClasses.add("")
//                itemClasses.add("")
//                itemClasses.add("")
//                itemClasses.add("")
//                itemClasses.add("")
//                itemClasses.add("")
//                itemClasses.add("")
//                itemClasses.add("")
//            }
//            mViewDataBinding.spinKit.visibility = View.GONE
//            locationsListAdapter.notifyDataSetChanged()
//        }, 5000)
//    }

    override fun clickshopItem(selectPosition: Int) {

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