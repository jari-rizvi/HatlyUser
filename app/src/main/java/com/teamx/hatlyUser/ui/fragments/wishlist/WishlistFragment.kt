package com.teamx.hatlyUser.ui.fragments.wishlist

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.AbsListView
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentWishlistBinding
import com.teamx.hatlyUser.ui.fragments.wishlist.adapter.WishListAdapter
import com.teamx.hatlyUser.ui.fragments.wishlist.modelWishList.Doc
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WishlistFragment : BaseFragment<FragmentWishlistBinding, WishlistViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_wishlist
    override val viewModel: Class<WishlistViewModel>
        get() = WishlistViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    lateinit var wishListArraylist: ArrayList<Doc>
    lateinit var hatlyPopularAdapter: WishListAdapter

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
        wishListArraylist = ArrayList()
        hatlyPopularAdapter = WishListAdapter(wishListArraylist)
        mViewDataBinding.recWishlist.adapter = hatlyPopularAdapter

        mViewModel.wishList(10,1)

        if (!mViewModel.wishListResponse.hasActiveObservers()) {
            mViewModel.wishListResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }
                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            wishListArraylist.clear()
                            wishListArraylist.addAll(data.docs)
                            hatlyPopularAdapter.notifyDataSetChanged()
                        }
                    }
                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        mViewDataBinding.root.snackbar(it.message!!)
                    }
                }
            }
        }


        mViewDataBinding.recWishlist.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                currentItems = layoutManager2.childCount
                totalItems = layoutManager2.itemCount
                scrollOutItems = layoutManager2.findFirstVisibleItemPosition()

                if(isScrolling && (currentItems + scrollOutItems == totalItems))
                {
                    isScrolling = false;
//                    fetchData()
                }
            }
        })

    }

//    private fun fetchData(){
//        mViewDataBinding.spinKit.visibility = View.VISIBLE
//        Handler(Looper.getMainLooper()).postDelayed({
//            for (i in 1..5) {
//                wishListArraylist.add("")
//                wishListArraylist.add("")
//                wishListArraylist.add("")
//                wishListArraylist.add("")
//                wishListArraylist.add("")
//                wishListArraylist.add("")
//                wishListArraylist.add("")
//                wishListArraylist.add("")
//                wishListArraylist.add("")
//                wishListArraylist.add("")
//                wishListArraylist.add("")
//            }
//            mViewDataBinding.spinKit.visibility = View.GONE
//            hatlyPopularAdapter.notifyDataSetChanged()
//        }, 5000)
//
//
//    }



}