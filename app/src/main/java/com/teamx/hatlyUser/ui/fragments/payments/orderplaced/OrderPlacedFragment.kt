package com.teamx.hatlyUser.ui.fragments.payments.orderplaced

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentOrderPlacedBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OrderPlacedFragment : BaseFragment<FragmentOrderPlacedBinding, OrderPlacedViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_order_placed
    override val viewModel: Class<OrderPlacedViewModel>
        get() = OrderPlacedViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private var orderId = ""

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
            orderId = bundle.getString("orderId", "")
        }

        mViewDataBinding.txtLogin12.setOnClickListener {
            val bundle1 = Bundle()
            bundle1.putString("orderId", orderId)
            findNavController().navigate(R.id.action_orderPlacedFragment_to_trackFragment,bundle1)
        }

        mViewDataBinding.txtLogin1.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)
        }

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("handleOnBackPressed", "handleOnBackPressed: back")
                findNavController().popBackStack(R.id.homeFragment, false)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )


    }


}