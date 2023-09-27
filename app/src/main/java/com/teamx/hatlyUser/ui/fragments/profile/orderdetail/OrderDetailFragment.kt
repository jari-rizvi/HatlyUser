package com.teamx.hatlyUser.ui.fragments.profile.orderdetail

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.AbsListView
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentOrderDetailBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.profile.orderdetail.adapter.OrderDetailAdapter
import com.teamx.hatlyUser.ui.fragments.profile.orderhistory.model.Product
import com.teamx.hatlyUser.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OrderDetailFragment : BaseFragment<FragmentOrderDetailBinding, OrderDetailViewModel>(),
    DialogHelperClass.Companion.ReviewProduct {

    override val layoutId: Int
        get() = R.layout.fragment_order_detail
    override val viewModel: Class<OrderDetailViewModel>
        get() = OrderDetailViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    lateinit var layoutManager1 : LinearLayoutManager
    lateinit var orderDetailAdapter : OrderDetailAdapter
    lateinit var productOrderHistoryList : ArrayList<Product>

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

        mViewDataBinding.txtLogin1.setOnClickListener {
            DialogHelperClass.reviewDialog(requireActivity(),this)
        }

        mViewDataBinding.txtLogin.setOnClickListener {

        }

        mViewDataBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        productOrderHistoryList = ArrayList()


        orderDetailAdapter = OrderDetailAdapter(productOrderHistoryList)
        mViewDataBinding.recLocations.adapter = orderDetailAdapter
        layoutManager1 = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.recLocations.layoutManager = layoutManager1


        sharedViewModel.orderHistory.observe(requireActivity()){data ->
            Log.d("sharedViewModel", "onViewCreated: ${data.shop.name}")

            mViewDataBinding.txtTitle.text = try {
                data.shop.name
            }catch (e : Exception){
                ""
            }

            mViewDataBinding.txtTitle1141.text = try {
                "#${data._id.substring(0, 6)}"
            }catch (e : Exception){
                ""
            }

            mViewDataBinding.txtAddress.text = try {
//                "${data.shop.address.googleMapAddress}"
                "${data.shippingAddress.floor} ${data.shippingAddress.building} ${data.shippingAddress.area} ${data.shippingAddress.streat}"
            }catch (e : Exception){
                ""
            }

            mViewDataBinding.txtTitle11123.text = try {
                "${data.subTotal}"
            }catch (e : Exception){
                ""
            }

            mViewDataBinding.txtTitle111323.text = try {
                "${data.deliveryCharges}"
            }catch (e : Exception){
                ""
            }

            mViewDataBinding.txtTitle1113233.text = try {
                "${data.total}"
            }catch (e : Exception){
                ""
            }



            Picasso.get().load(data.shop.image).into(mViewDataBinding.imgShop)

            productOrderHistoryList.addAll(data.products)
            orderDetailAdapter.notifyDataSetChanged()
        }

//        mViewDataBinding.recLocations.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
//                    isScrolling = true
//                }
//            }
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//                currentItems = layoutManager1.childCount
//                totalItems = layoutManager1.itemCount
//                scrollOutItems = layoutManager1.findFirstVisibleItemPosition()
//
//                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
//                    isScrolling = false
////                    fetchData()
//                }
//            }
//        })

    }

//    private fun fetchData() {
//        mViewDataBinding.spinKit.visibility = View.VISIBLE
//        Handler(Looper.getMainLooper()).postDelayed({
//            for (i in 1..5) {
//                productOrderHistoryList.add("")
//                productOrderHistoryList.add("")
//                productOrderHistoryList.add("")
//                productOrderHistoryList.add("")
//                productOrderHistoryList.add("")
//                productOrderHistoryList.add("")
//                productOrderHistoryList.add("")
//                productOrderHistoryList.add("")
//                productOrderHistoryList.add("")
//                productOrderHistoryList.add("")
//                productOrderHistoryList.add("")
//            }
//            mViewDataBinding.spinKit.visibility = View.GONE
//            orderDetailAdapter.notifyDataSetChanged()
//        }, 5000)
//    }


    override fun onSubmit() {
        Log.d("reviewDialog", "onSubmit: onSubmit")
        findNavController().navigate(R.id.action_orderDetailFragment_to_reviewSubmitedFragment)
    }

    override fun onCancel() {
        Log.d("reviewDialog", "onSubmit: onCancel")
    }


}