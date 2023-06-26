package com.teamx.hatlyUser.ui.fragments.wishlist

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
import com.teamx.hatlyUser.databinding.FragmentContactusBinding
import com.teamx.hatlyUser.databinding.FragmentSettingBinding
import com.teamx.hatlyUser.databinding.FragmentWalletBinding
import com.teamx.hatlyUser.databinding.FragmentWishlistBinding
import com.teamx.hatlyUser.ui.fragments.notification.adapter.NotificationAdapter
import com.teamx.hatlyUser.ui.fragments.setting.contactus.ContactUsViewModel
import com.teamx.hatlyUser.ui.fragments.setting.settings.SettingViewModel
import com.teamx.hatlyUser.ui.fragments.wallet.WalletViewModel
import com.teamx.hatlyUser.ui.fragments.wallet.adapter.WalletAdapter
import com.teamx.hatlyUser.ui.fragments.wishlist.adapter.WishListAdapter
import com.teamx.hatlyUser.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WishlistFragment : BaseFragment<FragmentWishlistBinding, WishlistViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_wishlist
    override val viewModel: Class<WishlistViewModel>
        get() = WishlistViewModel::class.java
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

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("handleOnBackPressed", "handleOnBackPressed: back")
                findNavController().popBackStack(R.id.homeFragment,false)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )


        val layoutManager2 = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        mViewDataBinding.recWishlist.layoutManager = layoutManager2

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

        val hatlyPopularAdapter = WishListAdapter(itemClasses)
        mViewDataBinding.recWishlist.adapter = hatlyPopularAdapter


    }



}