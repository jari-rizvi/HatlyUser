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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentWalletBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.wallet.adapter.WalletAdapter
import com.teamx.hatlyUser.ui.fragments.wallet.model.transaction.Doc
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

    var userId = ""

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

        userId = sharedViewModel.userData.value?._id ?: ""

        mViewDataBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        mViewDataBinding.imgTopUp.setOnClickListener {
            if (isAdded) {
                findNavController().navigate(R.id.action_walletFragment_to_topUpFragment)
            }
        }

        orderHistoryArrayList = ArrayList()

        val layoutManager2 = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.recWallet.layoutManager = layoutManager2
        walletAdapter = WalletAdapter(orderHistoryArrayList, this)
        mViewDataBinding.recWallet.adapter = walletAdapter




        if (!mViewModel.meResponse.hasActiveObservers()) {
            mViewModel.me()
        }
        mViewModel.meResponse.observe(requireActivity()) {
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
                        mViewDataBinding.txtTitle12.text = try {
                            "${data.wallet} ${requireActivity().getString(R.string.aed)}"
                        } catch (e: Exception) {
                            "null"
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

        if (!mViewModel.transactionHistoryResponse.hasActiveObservers() && userId.isNotEmpty()) {
            mViewModel.transactionHistory(userId,nextPage, 10)
        }

        mViewModel.transactionHistoryResponse.observe(requireActivity()) {
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

                        if (!hasNextPage) {
                            orderHistoryArrayList.clear()
                        }
                        data.docs.forEach {
                            try {
                                it.createdAt = TimeFormatter.formatTimeDifference(it.createdAt,requireActivity())
                            }catch (e : Exception){
                                e.printStackTrace()
                            }
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
                    if (isAdded) {

                    mViewDataBinding.root.snackbar(it.message!!)
                    }
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
                    if (hasNextPage && userId.isNotEmpty()) {
                        mViewModel.transactionHistory(userId,nextPage, 10)
                    }
                }
            }
        })


        mViewDataBinding.swiperefresh.setOnRefreshListener {
            mViewDataBinding.swiperefresh.isRefreshing = false
            reArrangeData()
            mViewModel.me()
        }


    }

    fun reArrangeData(){
        nextPage = 1
        mViewModel.transactionHistory(userId,nextPage, 10)
    }

    override fun clickshopItem(position: Int) {
        val orderHistoryModel = orderHistoryArrayList[position]


//        orderHistoryModel.isFromWallet = true
        sharedViewModel.setTransactionDetail(orderHistoryModel)
        if (isAdded) {
            findNavController().navigate(R.id.action_walletFragment_to_transactionDetailFragment)
        }
    }

    override fun clickCategoryItem(position: Int) {

    }

    override fun clickMoreItem(position: Int) {

    }


}