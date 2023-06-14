package com.teamx.hatlyUser.ui.fragments.profile.orderdetail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentOrderDetailBinding
import com.teamx.hatlyUser.databinding.FragmentOrderHistoryBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.profile.Locations.adapter.LocationsListAdapter
import com.teamx.hatlyUser.ui.fragments.profile.orderdetail.adapter.OrderDetailAdapter
import com.teamx.hatlyUser.ui.fragments.profile.orderhistory.OrderHistoryViewModel
import com.teamx.hatlyUser.ui.fragments.profile.orderhistory.adapter.OrderHistoryAdapter
import com.teamx.hatlyUser.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OrderDetailFragment : BaseFragment<FragmentOrderDetailBinding, OrderDetailViewModel>(),
    HatlyShopInterface,DialogHelperClass.Companion.ReviewProduct {

    override val layoutId: Int
        get() = R.layout.fragment_order_detail
    override val viewModel: Class<OrderDetailViewModel>
        get() = OrderDetailViewModel::class.java
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

        val layoutManager1 = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.recLocations.layoutManager = layoutManager1

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

        val adapter = OrderDetailAdapter(itemClasses, this)
        mViewDataBinding.recLocations.adapter = adapter

        mViewDataBinding.txtLogin1.setOnClickListener {
            DialogHelperClass.reviewDialog(requireActivity(),this)
        }

        mViewDataBinding.txtLogin.setOnClickListener {

        }


    }

    override fun clickshopItem(position: Int) {

    }

    override fun clickMoreItem(position: Int) {

    }

    override fun onSubmit() {
        Log.d("reviewDialog", "onSubmit: onSubmit")
        findNavController().navigate(R.id.action_orderDetailFragment_to_reviewSubmitedFragment)
    }

    override fun onCancel() {
        Log.d("reviewDialog", "onSubmit: onCancel")
    }


}