package com.teamx.hatlyUser.ui.fragments.profile.specialOrderHistory

import android.os.Build
import android.os.Bundle
import android.util.Log
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
import com.teamx.hatlyUser.databinding.FragmentSpecialOrderHistoryBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.profile.specialOrderHistory.adapter.SpecialOrderHistoryAdapter
import com.teamx.hatlyUser.ui.fragments.profile.specialOrderHistory.model.Doc
import com.teamx.hatlyUser.ui.fragments.special.specialorder.model.DeliveredParcel
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SpecialOrderHistoryFragment :
    BaseFragment<FragmentSpecialOrderHistoryBinding, SpecialOrderHistoryViewModel>(),
    HatlyShopInterface {

    override val layoutId: Int
        get() = R.layout.fragment_special_order_history
    override val viewModel: Class<SpecialOrderHistoryViewModel>
        get() = SpecialOrderHistoryViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var specialHistoryArrayList: ArrayList<Doc>
    private lateinit var specialOrderHistoryAdapter: SpecialOrderHistoryAdapter

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

        specialHistoryArrayList = ArrayList()

        val layoutManager1 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.recSpecialOrderHistory.layoutManager = layoutManager1
        specialOrderHistoryAdapter = SpecialOrderHistoryAdapter(specialHistoryArrayList, this)
        mViewDataBinding.recSpecialOrderHistory.adapter = specialOrderHistoryAdapter


        if (!mViewModel.allParcelResponse.hasActiveObservers()) {
            mViewModel.allParcel("pending", 10, 1)
        }

        mViewModel.allParcelResponse.observe(requireActivity()) {
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
                        if (data.hasNextPage) {
                            nextPage = data.nextPage
                        }
                        Log.d("activeDelieverResponse", "onViewCreated: ${data.docs}")
                        specialHistoryArrayList.clear()
                        data.docs?.let { it1 -> specialHistoryArrayList.addAll(it1) }
                        specialOrderHistoryAdapter.notifyDataSetChanged()
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

        mViewDataBinding.recSpecialOrderHistory.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                currentItems = layoutManager1.childCount
                totalItems = layoutManager1.itemCount
                scrollOutItems = layoutManager1.findFirstVisibleItemPosition()

                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false
                    if (hasNextPage) {
                        mViewModel.allParcel("pending", 10, nextPage)
                    }
//                    fetchData()
                }
            }
        })
    }

//    private fun fetchData() {
//        mViewDataBinding.spinKit.visibility = View.VISIBLE
//        Handler(Looper.getMainLooper()).postDelayed({
//            for (i in 1..5) {
//                orderHistoryArrayList.add("")
//                orderHistoryArrayList.add("")
//                orderHistoryArrayList.add("")
//                orderHistoryArrayList.add("")
//                orderHistoryArrayList.add("")
//                orderHistoryArrayList.add("")
//                orderHistoryArrayList.add("")
//                orderHistoryArrayList.add("")
//                orderHistoryArrayList.add("")
//                orderHistoryArrayList.add("")
//                orderHistoryArrayList.add("")
//            }
//            mViewDataBinding.spinKit.visibility = View.GONE
//            orderHistoryAdapter.notifyDataSetChanged()
//        }, 5000)
//    }

    override fun clickshopItem(position: Int) {
        val parcelModel = specialHistoryArrayList[position]

        sharedViewModel.setParcelOrderHistory(parcelModel)
        findNavController().navigate(R.id.action_specialOrderHistoryFragment_to_specialOrderDetailFragment)
    }

    override fun clickCategoryItem(position: Int) {

    }

    override fun clickMoreItem(position: Int) {

    }


}