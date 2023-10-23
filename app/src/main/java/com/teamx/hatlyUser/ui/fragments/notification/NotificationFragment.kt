package com.teamx.hatlyUser.ui.fragments.notification

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentNotificationBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.notification.adapter.NotificationAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NotificationFragment : BaseFragment<FragmentNotificationBinding, NotificationViewModel>(),
    HatlyShopInterface {

    override val layoutId: Int
        get() = R.layout.fragment_notification
    override val viewModel: Class<NotificationViewModel>
        get() = NotificationViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    var layoutManager2: LinearLayoutManager? = null

    var isScrolling = false
    var currentItems = 0
    var totalItems = 0
    var scrollOutItems = 0

    private lateinit var notificationArrayList: ArrayList<String>
    private lateinit var hatlyPopularAdapter: NotificationAdapter

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

        mViewDataBinding.imgBack1.setOnClickListener {
            findNavController().popBackStack()
        }

        notificationArrayList = ArrayList()
        layoutManager2 = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.recNotification.layoutManager = layoutManager2
        hatlyPopularAdapter = NotificationAdapter(notificationArrayList)
        mViewDataBinding.recNotification.adapter = hatlyPopularAdapter

        notificationArrayList.add("")
        notificationArrayList.add("")
        notificationArrayList.add("")
        notificationArrayList.add("")
        notificationArrayList.add("")
        notificationArrayList.add("")
        notificationArrayList.add("")
        notificationArrayList.add("")
        notificationArrayList.add("")
        notificationArrayList.add("")
        notificationArrayList.add("")
        notificationArrayList.add("")
        notificationArrayList.add("")
        notificationArrayList.add("")
        notificationArrayList.add("")
        notificationArrayList.add("")
        notificationArrayList.add("")
        notificationArrayList.add("")
        notificationArrayList.add("")
        notificationArrayList.add("")
        notificationArrayList.add("")
        notificationArrayList.add("")
        notificationArrayList.add("")
        notificationArrayList.add("")
        notificationArrayList.add("")




//        mViewDataBinding.recNotification.addOnScrollListener(object :
//            RecyclerView.OnScrollListener() {
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
//                currentItems = layoutManager2!!.childCount
//                totalItems = layoutManager2!!.itemCount
//                scrollOutItems = layoutManager2!!.findFirstVisibleItemPosition()
//
//                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
//                    isScrolling = false
//                    fetchData()
//                }
//            }
//        })


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
//
//
//    }

    override fun clickshopItem(position: Int) {

    }

    override fun clickCategoryItem(position: Int) {

    }

    override fun clickMoreItem(position: Int) {

    }


}