package com.teamx.hatlyUser.ui.fragments.foods.review

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.teamx.hatlyUser.databinding.FragmentReviewBinding
import com.teamx.hatlyUser.ui.fragments.foods.review.adapter.ReviewAdapter
import com.teamx.hatlyUser.ui.fragments.foods.review.modelReviewList.Doc
import com.teamx.hatlyUser.utils.TimeFormatter
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewFragment : BaseFragment<FragmentReviewBinding, ReviewViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_review
    override val viewModel: Class<ReviewViewModel>
        get() = ReviewViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var reviewArrayList: ArrayList<Doc>
    lateinit var layoutManager2: LinearLayoutManager
    private lateinit var hatlyPopularAdapter: ReviewAdapter

    var isScrolling = false
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

        val bundle = arguments

        val shopId = bundle?.getString("shopId","")

        mViewDataBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        layoutManager2 = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        mViewDataBinding.recCart.layoutManager = layoutManager2

        reviewArrayList = ArrayList()
        hatlyPopularAdapter = ReviewAdapter(reviewArrayList)
        mViewDataBinding.recCart.adapter = hatlyPopularAdapter

        if (shopId != null) {
            mViewModel.reviewList(shopId,10,1)
        }

        if (!mViewModel.reviewListResponse.hasActiveObservers()) {
            mViewModel.reviewListResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            reviewArrayList.clear()

                            data.docs.forEach {

                                try {
                                    it.createdAt = TimeFormatter.formatTimeDifference(it.createdAt)
                                }catch (e : Exception){
                                    e.printStackTrace()
                                }
                                reviewArrayList.add(it)
                            }

//                            reviewArrayList.addAll(data.docs)
                            hatlyPopularAdapter.notifyDataSetChanged()
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
        }



        mViewDataBinding.recCart.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
                    isScrolling = false
//                    fetchData()
                }
            }
        })
    }

//    private fun fetchData() {
//        mViewDataBinding.spinKit.visibility = View.VISIBLE
//        Handler(Looper.getMainLooper()).postDelayed({
//            for (i in 1..5) {
//                reviewArrayList.add("")
//                reviewArrayList.add("")
//                reviewArrayList.add("")
//                reviewArrayList.add("")
//                reviewArrayList.add("")
//                reviewArrayList.add("")
//                reviewArrayList.add("")
//                reviewArrayList.add("")
//                reviewArrayList.add("")
//                reviewArrayList.add("")
//                reviewArrayList.add("")
//            }
//            mViewDataBinding.spinKit.visibility = View.GONE
//            hatlyPopularAdapter.notifyDataSetChanged()
//        }, 5000)
//    }
}