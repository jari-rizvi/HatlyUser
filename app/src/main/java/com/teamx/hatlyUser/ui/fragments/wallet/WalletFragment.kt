package com.teamx.hatlyUser.ui.fragments.wallet

import android.os.Build
import android.os.Bundle
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
import com.teamx.hatlyUser.databinding.FragmentWalletBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.profile.orderhistory.model.Doc
import com.teamx.hatlyUser.ui.fragments.wallet.adapter.WalletAdapter
import com.teamx.hatlyUser.utils.TimeFormatter
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WalletFragment : BaseFragment<FragmentWalletBinding, WalletViewModel>(), HatlyShopInterface {

    override val layoutId: Int
        get() = R.layout.fragment_wallet
    override val viewModel: Class<WalletViewModel>
        get() = WalletViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var orderHistoryArrayList: ArrayList<Doc>
    private lateinit var walletAdapter: WalletAdapter

    var isScrolling = false
    var hasNextPage = false
    var nextPage = 1
    var currentItems = 0
    var totalItems = 0
    var scrollOutItems = 0


    @RequiresApi(Build.VERSION_CODES.O)
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

        mViewDataBinding.imgTopUp.setOnClickListener {
            if (isAdded) {
                findNavController().navigate(R.id.action_walletFragment_to_topUpFragment)
            }
        }

        orderHistoryArrayList = ArrayList()

        val layoutManager2 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.recWallet.layoutManager = layoutManager2
        walletAdapter = WalletAdapter(orderHistoryArrayList, this)
        mViewDataBinding.recWallet.adapter = walletAdapter

        if (!mViewModel.orderHistoryResponse.hasActiveObservers()) {
            mViewModel.orderHistory(nextPage, 10)
        }

        mViewModel.me()
        if (!mViewModel.meResponse.hasActiveObservers()) {
            mViewModel.meResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            mViewDataBinding.txtTitle12.text = try {
                                data.wallet.toString()
                            } catch (e: Exception) {
                                ""
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

        mViewModel.orderHistoryResponse.observe(requireActivity()) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }

                Resource.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    it.data?.let { data ->

                        if (!hasNextPage) {
                            orderHistoryArrayList.clear()
                        }
                        data.docs?.forEach {
                            it.createdAt = TimeFormatter.formatTimeDifference(it.createdAt)
                            orderHistoryArrayList.add(it)
                        }
//                        data.docs?.let { it1 -> orderHistoryArrayList.addAll(it1) }
                        walletAdapter.notifyDataSetChanged()

                        nextPage = data.nextPage ?: 1
                        hasNextPage = data.hasNextPage
                    }
                }

                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    mViewDataBinding.root.snackbar(it.message!!)
                }
            }
        }


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
                    if (hasNextPage) {
                        mViewModel.orderHistory(nextPage, 10)
                    }
                }
            }
        })


    }

    override fun clickshopItem(position: Int) {
        val orderHistoryModel = orderHistoryArrayList[position]
        orderHistoryModel.isFromWallet = true
        sharedViewModel.setOrderHistory(orderHistoryModel)
        if (isAdded) {
            findNavController().navigate(R.id.action_walletFragment_to_orderDetailFragment)
        }
    }

    override fun clickCategoryItem(position: Int) {

    }

    override fun clickMoreItem(position: Int) {

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
//            hatlyPopularAdapter.notifyDataSetChanged()
//        }, 5000)
//    }
}