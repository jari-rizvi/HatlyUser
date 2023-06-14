package com.teamx.hatlyUser.ui.fragments.profile.orderhistory

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentOrderHistoryBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.profile.Locations.adapter.LocationsListAdapter
import com.teamx.hatlyUser.ui.fragments.profile.orderhistory.adapter.OrderHistoryAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OrderHistoryFragment : BaseFragment<FragmentOrderHistoryBinding, OrderHistoryViewModel>(),
    HatlyShopInterface {

    override val layoutId: Int
        get() = R.layout.fragment_order_history
    override val viewModel: Class<OrderHistoryViewModel>
        get() = OrderHistoryViewModel::class.java
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

        val adapter = OrderHistoryAdapter(itemClasses, this)
        mViewDataBinding.recLocations.adapter = adapter



    }

    override fun clickshopItem(position: Int) {
        findNavController().navigate(R.id.action_orderHistoryFragment_to_orderDetailFragment)
    }

    override fun clickMoreItem(position: Int) {

    }


}