package com.teamx.hatlyUser.ui.fragments.wallet

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.AbsListView
import android.widget.CompoundButton
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentContactusBinding
import com.teamx.hatlyUser.databinding.FragmentSettingBinding
import com.teamx.hatlyUser.databinding.FragmentWalletBinding
import com.teamx.hatlyUser.ui.fragments.notification.adapter.NotificationAdapter
import com.teamx.hatlyUser.ui.fragments.setting.contactus.ContactUsViewModel
import com.teamx.hatlyUser.ui.fragments.setting.settings.SettingViewModel
import com.teamx.hatlyUser.ui.fragments.wallet.adapter.WalletAdapter
import com.teamx.hatlyUser.ui.fragments.wishlist.adapter.WishListAdapter
import com.teamx.hatlyUser.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WalletFragment : BaseFragment<FragmentWalletBinding, WalletViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_wallet
    override val viewModel: Class<WalletViewModel>
        get() = WalletViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    lateinit var itemClasses: ArrayList<String>
    lateinit var hatlyPopularAdapter: WalletAdapter

    var isScrolling = false
    var currentItems = 0
    var totalItems = 0
    var scrollOutItems = 0


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

        val layoutManager2 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        mViewDataBinding.recWallet.layoutManager = layoutManager2

        itemClasses = ArrayList()

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


        hatlyPopularAdapter = WalletAdapter(itemClasses)
        mViewDataBinding.recWallet.adapter = hatlyPopularAdapter
        mViewDataBinding.recWallet.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                currentItems = layoutManager2.childCount
                totalItems = layoutManager2.itemCount
                scrollOutItems = layoutManager2.findFirstVisibleItemPosition()

                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false;
                    fetchData()
                }
            }
        })


    }

    private fun fetchData() {
        mViewDataBinding.spinKit.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            for (i in 1..5) {
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
            }
            mViewDataBinding.spinKit.visibility = View.GONE
            hatlyPopularAdapter.notifyDataSetChanged()
        }, 5000)
    }
}