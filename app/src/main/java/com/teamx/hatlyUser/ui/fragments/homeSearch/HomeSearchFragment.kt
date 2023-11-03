package com.teamx.hatlyUser.ui.fragments.homeSearch

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.databinding.ViewDataBinding
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.constants.NetworkCallPointsNest
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentHomeSearchBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.home.model.FcmModel
import com.teamx.hatlyUser.ui.fragments.homeSearch.adapter.CategoryHomeSearchInterface
import com.teamx.hatlyUser.ui.fragments.homeSearch.adapter.HomeRecentSearchAdapter
import com.teamx.hatlyUser.ui.fragments.homeSearch.adapter.HomeSearchTitleAdapter
import com.teamx.hatlyUser.ui.fragments.homeSearch.adapter.RecentHomeSearchInterface
import com.teamx.hatlyUser.utils.enum_.Marts
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeSearchFragment : BaseFragment<FragmentHomeSearchBinding, HomeSearchViewModel>(),
    HatlyShopInterface,
    RecentHomeSearchInterface, CategoryHomeSearchInterface {

    override val layoutId: Int
        get() = R.layout.fragment_home_search
    override val viewModel: Class<HomeSearchViewModel>
        get() = HomeSearchViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var categoryArray: ArrayList<FcmModel>
    private lateinit var recentArray: ArrayList<String>
    private lateinit var homeSearchTitleAdapter: HomeSearchTitleAdapter
    private lateinit var homeSearchRecentAdapter: HomeRecentSearchAdapter

    private var categoryStr = ""
    private var typeStr = ""

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

        categoryArray = ArrayList()
        recentArray = ArrayList()

        categoryArray.add(FcmModel("Food", true))
        categoryArray.add(FcmModel("Grocery", false))
        categoryArray.add(FcmModel("Health & beauty", false))
        categoryArray.add(FcmModel("Home Business", false))
        categoryArray.add(FcmModel("Grocery", false))

        val layoutManager1 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.recCategories.layoutManager = layoutManager1
        homeSearchTitleAdapter = HomeSearchTitleAdapter(categoryArray, this)
        mViewDataBinding.recCategories.adapter = homeSearchTitleAdapter

        recentArray.add("Hello how")
        recentArray.add("are you doing")
        recentArray.add("where")
        recentArray.add("now")
        recentArray.add("hello")

        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)
        homeSearchRecentAdapter = HomeRecentSearchAdapter(recentArray, this)
        mViewDataBinding.recRecentSearch.layoutManager = staggeredGridLayoutManager
        mViewDataBinding.recRecentSearch.adapter = homeSearchRecentAdapter

        mViewDataBinding.inpSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if (s.isNotEmpty()) {
                        mViewDataBinding.recentLayout.visibility = View.GONE
                    } else {
                        mViewDataBinding.recentLayout.visibility = View.VISIBLE
                    }
                }
            }

        })

//        mViewDataBinding.inpSearch.setOnEditorActionListener(OnEditorActionListener  { v, actionId, event ->
//            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                performSearch()
//                return@OnEditorActionListener true
//            }
//            false
//        })

        mViewDataBinding.inpSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
            }
            true
        }

        mViewDataBinding.txtItems.setOnClickListener {
            mViewDataBinding.txtItems.isChecked = true
            mViewDataBinding.txtShops.isChecked = false
            typeStr = "item"
        }

        mViewDataBinding.txtShops.setOnClickListener {
            mViewDataBinding.txtItems.isChecked = false
            mViewDataBinding.txtShops.isChecked = true
            typeStr = "shop"
        }


        if (!mViewModel.homeSearchResponse.hasActiveObservers()) {
            mViewModel.homeSearchResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            Log.d("homeSearchResponse", "onViewCreated: $data")
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
    }

    private fun performSearch() {
        val search = mViewDataBinding.inpSearch.text.toString()
        mViewModel.homeSearch(search,categoryStr,typeStr,10,1)
    }

    override fun clickshopItem(position: Int) {

    }

    override fun clickCategoryItem(position: Int) {

    }

    override fun clickMoreItem(position: Int) {

    }

    override fun clickRecent(position: Int) {
        val modelRecent = recentArray[position]
//        mViewDataBinding.inpSearch.requestFocus()
//        val pos: Int = mViewDataBinding.inpSearch.text.toString().length
//        mViewDataBinding.inpSearch.setSelection(pos)
        mViewDataBinding.inpSearch.setText(modelRecent)
    }

    override fun clickcategory(position: Int) {
        categoryArray.forEach { it.success = false }
        categoryArray[position].success = true
        categoryStr = categoryArray[position].message
        homeSearchTitleAdapter.notifyDataSetChanged()
    }


}