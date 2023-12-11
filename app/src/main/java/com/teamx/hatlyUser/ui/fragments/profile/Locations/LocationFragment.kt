package com.teamx.hatlyUser.ui.fragments.profile.Locations

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentLocationBinding
import com.teamx.hatlyUser.ui.fragments.auth.login.Model.Location
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.profile.Locations.adapter.LocationsListAdapter
import com.teamx.hatlyUser.utils.PrefHelper
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


    private lateinit var getAddressArray: ArrayList<Location>
    private lateinit var locationsListAdapter: LocationsListAdapter

    private var fromParcel = ""

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
            fromParcel = bundle.getString("fromParcel", "")
            Log.d("fareCalculation", "fromParcel not empty: ${fromParcel}")
        }

        mViewDataBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        mViewDataBinding.txtAddLocation.setOnClickListener {
            val userData = PrefHelper.getInstance(requireActivity()).getUserData()

            if (userData != null) {
                if (userData.location != null) {
                    userData.location!!.isAction = "Add"
                }
            }

            sharedViewModel.setlocationmodel(userData?.location)

            findNavController().navigate(R.id.action_locationFragment_to_mapFragment)
        }

        getAddressArray = ArrayList()

        mViewDataBinding.recLocations.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        locationsListAdapter = LocationsListAdapter(getAddressArray, this)
        mViewDataBinding.recLocations.adapter = locationsListAdapter

//        val itemDragSwipeCallback = ItemDragSwipeCallback(
//            requireActivity(),
//            com.teamx.hatlyUser.R.color.white,
//            com.teamx.hatlyUser.R.drawable.notification_,
//            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
//            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
//            object : ItemDragSwipeCallback.OnTouchListener {
//                override fun onMove(
//                    recyclerView: RecyclerView?,
//                    viewHolder: RecyclerView.ViewHolder?,
//                    target: RecyclerView.ViewHolder?
//                ): Boolean {
//                    // Implement your logic for item movement
//                    return true // Return true if the item was moved, false otherwise
//                }
//
//                override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
//                    // Implement your logic for item swiping
//                }
//            }
//        )
//
//
//        val swipeHelper: SwipeHelper = object : SwipeHelper(requireActivity(), mViewDataBinding.recLocations) {
//            override fun instantiateUnderlayButton(
//                viewHolder: RecyclerView.ViewHolder,
//                underlayButtons: MutableList<UnderlayButton>
//            ) {
//                underlayButtons.add(UnderlayButton(
//                    requireActivity(),
//                    "",
//                    R.drawable.notification_,
//                    Color.parseColor("#4CAF50")
//                ) {
//                    Toast.makeText(context, "Delete $it", Toast.LENGTH_SHORT).show()
//                })
//            }
//        }
//
//
//        val itemTouchHelper = ItemTouchHelper(swipeHelper)
//        itemTouchHelper.attachToRecyclerView(mViewDataBinding.recLocations)

//        val itemTouchHelper = ItemTouchHelper(itemDragSwipeCallback)
//        itemTouchHelper.attachToRecyclerView(mViewDataBinding.recLocations)

//        mViewDataBinding.recLocations.addOnItemTouchListener(
//            RecyclerItemClickListener(this, recyclerView, object : RecyclerItemClickListener.OnItemClickListener {
//                override fun onItemClick(view: View, position: Int) {
//                    // Handle item click here
//                }
//            })
//        )


        mViewModel.getAlAddress()

        if (!mViewModel.getAlAddressResponse.hasActiveObservers()) {
            mViewModel.getAlAddressResponse.observe(requireActivity()) {
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
                            if (data.isNotEmpty()) {
//                                data[0].isSelected = true

                                if (fromParcel.isNotEmpty()) {
                                    data.forEach {
                                        it.isFromSender = true
                                        getAddressArray.add(it)
                                    }
                                } else {
                                    getAddressArray.addAll(data)
                                }
                                locationsListAdapter.notifyDataSetChanged()
                            }
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        if (isAdded) {
                            mViewDataBinding.root.snackbar(it.message!!)
                        }
                    }
                }
            }
        }

        if (!mViewModel.setDefaultAddressResponse.hasActiveObservers()) {
            mViewModel.setDefaultAddressResponse.observe(requireActivity()) {
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
                            if (data.success) {
                                val userData = PrefHelper.getInstance(requireActivity()).getUserData()
                                userData!!.location = data.data!!
                                PrefHelper.getInstance(requireActivity()).setUserData(userData)
                                sharedViewModel.setlocationmodel(userData.location)
                                mViewDataBinding.root.snackbar(data.message)
                            }
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        if (isAdded) {

                            mViewDataBinding.root.snackbar(it.message!!)
                        }
                    }
                }
            }
        }

        if (!mViewModel.deleteAddressResponse.hasActiveObservers()) {
            mViewModel.deleteAddressResponse.observe(requireActivity()) {
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
                            getAddressArray.removeAt(locationPosition)
                            locationsListAdapter.notifyItemRemoved(locationPosition)
                            locationsListAdapter.notifyItemRangeChanged(
                                locationPosition,
                                getAddressArray.size - locationPosition
                            )
                            mViewDataBinding.root.snackbar("Deleted")
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        if (isAdded) {

                            mViewDataBinding.root.snackbar(it.message!!)
                        }
                    }
                }
            }
        }
    }

    private var locationPosition = -1


    override fun clickshopItem(selectPosition: Int) {
        if (fromParcel.isNotEmpty()) {
            val locationModel = getAddressArray[selectPosition]
            when (fromParcel) {
                "senderData" -> {
                    locationModel.isFromSender = true
                }

                "reciverData" -> {
                    locationModel.isFromSender = false
                }
            }
            sharedViewModel.setParcelLocation(locationModel)
            findNavController().popBackStack()
        } else {
            getAddressArray.forEach { it.isDefault = false }
            getAddressArray[selectPosition].isDefault = true
            locationsListAdapter.notifyDataSetChanged()
            mViewModel.setDefaultAddress(getAddressArray[selectPosition]._id)
        }
    }

    override fun clickCategoryItem(updatePosition: Int) {
        val locationModel = getAddressArray[updatePosition]
        locationModel.isAction = "Update"
        sharedViewModel.setlocationmodel(locationModel)
        findNavController().navigate(R.id.action_locationFragment_to_mapFragment)

    }

    override fun clickMoreItem(deletePosition: Int) {
        val locationModel = getAddressArray[deletePosition]
        locationPosition = deletePosition
        mViewModel.deleteAddress(locationModel._id)
    }


}