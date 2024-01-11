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
import com.google.gson.JsonObject
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.constants.NetworkCallPointsNest
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentWishlistBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.wishlist.adapter.WishListAdapter
import com.teamx.hatlyUser.ui.fragments.wishlist.modelWishList.Doc
import com.teamx.hatlyUser.utils.enum_.Marts
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException


@AndroidEntryPoint
class WishlistFragment : BaseFragment<FragmentWishlistBinding, WishlistViewModel>(),
    HatlyShopInterface {

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
                findNavController().popBackStack(R.id.homeFragment, false)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )


        val layoutManager2 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        mViewDataBinding.recWishlist.layoutManager = layoutManager2
        wishListArraylist = ArrayList()
        hatlyPopularAdapter = WishListAdapter(wishListArraylist, this)
        mViewDataBinding.recWishlist.adapter = hatlyPopularAdapter



        if (!mViewModel.favRemoveResponse.hasActiveObservers()) {
            mViewModel.favRemoveResponse.observe(requireActivity()) {
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
                            if (data.success) {

                                wishListArraylist.removeAt(wishListPosition)
                                hatlyPopularAdapter.notifyItemRemoved(wishListPosition)
                                hatlyPopularAdapter.notifyItemRangeRemoved(
                                    wishListPosition,
                                    wishListArraylist.size
                                )

                                if (isAdded) {
                                    mViewDataBinding.root.snackbar(getString(R.string.product_removed_from_wishlist))
                                }

                                if (wishListArraylist.isEmpty()) {
                                    findNavController().popBackStack()
                                }
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
        }

        mViewModel.wishList(10, 1)

        if (!mViewModel.wishListResponse.hasActiveObservers()) {
            mViewModel.wishListResponse.observe(requireActivity()) {
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
                            wishListArraylist.clear()
                            wishListArraylist.addAll(data.docs)
                            hatlyPopularAdapter.notifyDataSetChanged()
                            Log.d("wishListArraylist", "onViewCreated: ${data.docs[0]}")
                            if (wishListArraylist.isNotEmpty()) {
                                mViewDataBinding.recWishlist.visibility = View.VISIBLE
                                mViewDataBinding.textView22545454.visibility = View.GONE
                            }
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        if (isAdded) {
                            mViewDataBinding.root.snackbar(it.message!!)
                            Log.d("wishListArraylist", "onViewCreated: ${it.message!!}")
                        }
                    }
                }
            }
        }


        mViewDataBinding.recWishlist.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
//                    fetchData()
                }
            }
        })

    }

    var wishListPosition = -1

    override fun clickshopItem(position: Int) {

        val wishListModel = wishListArraylist[position]
        if (wishListModel.shop._id.isNotEmpty()) {
            val params = JsonObject()
            try {
                params.addProperty("itemId", wishListModel.shop._id)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            wishListPosition = position
            mViewModel.favRemove(params)
        }
    }

    override fun clickCategoryItem(position: Int) {
        val wishListModel = wishListArraylist[position]
        val bundle = Bundle()
        bundle.putString("itemId", wishListModel.shop._id)
        NetworkCallPointsNest.MART = Marts.FOOD
        findNavController().navigate(R.id.action_wishListFragment_to_foodsShopHomeFragment, bundle)
    }

    override fun clickMoreItem(position: Int) {

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