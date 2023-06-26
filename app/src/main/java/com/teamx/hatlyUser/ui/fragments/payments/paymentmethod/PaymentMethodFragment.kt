package com.teamx.hatlyUser.ui.fragments.payments.paymentmethod

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentCartBinding
import com.teamx.hatlyUser.databinding.FragmentCheckOutBinding
import com.teamx.hatlyUser.databinding.FragmentContactusBinding
import com.teamx.hatlyUser.databinding.FragmentPaymentMethodBinding
import com.teamx.hatlyUser.databinding.FragmentSettingBinding
import com.teamx.hatlyUser.databinding.FragmentWalletBinding
import com.teamx.hatlyUser.databinding.FragmentWishlistBinding
import com.teamx.hatlyUser.ui.fragments.payments.cart.adapter.CartAdapter
import com.teamx.hatlyUser.ui.fragments.notification.adapter.NotificationAdapter
import com.teamx.hatlyUser.ui.fragments.payments.cart.CartViewModel
import com.teamx.hatlyUser.ui.fragments.payments.checkout.CheckOutViewModel
import com.teamx.hatlyUser.ui.fragments.payments.checkout.adapter.CheckOutAdapter
import com.teamx.hatlyUser.ui.fragments.setting.contactus.ContactUsViewModel
import com.teamx.hatlyUser.ui.fragments.setting.settings.SettingViewModel
import com.teamx.hatlyUser.ui.fragments.wallet.WalletViewModel
import com.teamx.hatlyUser.ui.fragments.wallet.adapter.WalletAdapter
import com.teamx.hatlyUser.ui.fragments.wishlist.WishlistViewModel
import com.teamx.hatlyUser.ui.fragments.wishlist.adapter.WishListAdapter
import com.teamx.hatlyUser.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PaymentMethodFragment : BaseFragment<FragmentPaymentMethodBinding, PaymentMethodViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_payment_method
    override val viewModel: Class<PaymentMethodViewModel>
        get() = PaymentMethodViewModel::class.java
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



    }


}