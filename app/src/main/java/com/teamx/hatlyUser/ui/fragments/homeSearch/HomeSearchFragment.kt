package com.teamx.hatlyUser.ui.fragments.homeSearch

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AbsListView
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.constants.NetworkCallPointsNest
import com.teamx.hatlyUser.constants.NetworkCallPointsNest.Companion.MART
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentHomeSearchBinding
import com.teamx.hatlyUser.ui.fragments.home.model.FcmModel
import com.teamx.hatlyUser.ui.fragments.homeSearch.adapter.CategoryHomeSearchInterface
import com.teamx.hatlyUser.ui.fragments.homeSearch.adapter.HomeRecentSearchAdapter
import com.teamx.hatlyUser.ui.fragments.homeSearch.adapter.HomeSearchAdapter
import com.teamx.hatlyUser.ui.fragments.homeSearch.adapter.HomeSearchTitleAdapter
import com.teamx.hatlyUser.ui.fragments.homeSearch.adapter.RecentHomeSearchInterface
import com.teamx.hatlyUser.ui.fragments.homeSearch.model.Doc
import com.teamx.hatlyUser.ui.fragments.products.adapter.interfaces.ProductPreviewInterface
import com.teamx.hatlyUser.utils.PrefHelper
import com.teamx.hatlyUser.utils.enum_.Marts
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeSearchFragment : BaseFragment<FragmentHomeSearchBinding, HomeSearchViewModel>(),
    ProductPreviewInterface,
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

//    private var categoryStr = ""

    var categoryStr: String? = null
    private var typeStr = "item"
    private var categoryPosition = 0

    private lateinit var homeSearchArrayList: ArrayList<Doc>
    lateinit var layoutManager2: LinearLayoutManager
    private lateinit var hatlyPopularAdapter: HomeSearchAdapter

    var currentItems = 0
    var totalItems = 0
    var scrollOutItems = 0
    var isScrolling = false
    var hasNextPage = false
    var nextPage = 0

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

        categoryArray = ArrayList()
        recentArray = ArrayList()
        homeSearchArrayList = ArrayList()

        categoryArray.clear()
        categoryArray.add(FcmModel("All", false))
        categoryArray.add(FcmModel("Food", false))
        categoryArray.add(FcmModel("Grocery", false))
        categoryArray.add(FcmModel("Health & beauty", false))
        categoryArray.add(FcmModel("Home Business", false))


        categoryArray[categoryPosition].success = true

        val layoutManager1 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.recCategories.layoutManager = layoutManager1
        homeSearchTitleAdapter = HomeSearchTitleAdapter(categoryArray, this)
        mViewDataBinding.recCategories.adapter = homeSearchTitleAdapter


        try {
            val updatedWords = PrefHelper.getInstance(requireActivity()).loadSavedWords()
            updatedWords.forEach {
                recentArray.add(it)
            }
            Log.d("updatedWords", "onViewCreated: ${updatedWords}")
        } catch (e: Exception) {
            e.printStackTrace()
        }


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
                        mViewDataBinding.typeLayout.visibility = View.VISIBLE
                    } else {
                        mViewDataBinding.recentLayout.visibility = View.VISIBLE
                        mViewDataBinding.typeLayout.visibility = View.GONE
                    }
                }
            }

        })



        layoutManager2 = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.recHomeSearch.layoutManager = layoutManager2
        hatlyPopularAdapter = HomeSearchAdapter(homeSearchArrayList, this)
        mViewDataBinding.recHomeSearch.adapter = hatlyPopularAdapter




        mViewDataBinding.inpSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                clickcategory(0)
                categoryStr = null
                performSearch()
            }
            true
        }

        mViewDataBinding.txtItems.setOnClickListener {
            mViewDataBinding.txtItems.isChecked = true
            mViewDataBinding.txtShops.isChecked = false
            typeStr = "item"
            performSearch()
        }

        mViewDataBinding.txtShops.setOnClickListener {
            mViewDataBinding.txtItems.isChecked = false
            mViewDataBinding.txtShops.isChecked = true
            typeStr = "shop"
            performSearch()
        }




        if (!mViewModel.homeSearchResponse.hasActiveObservers()) {
            performSearch()
        }


        mViewModel.homeSearchResponse.observe(requireActivity()) {
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

                        PrefHelper.getInstance(requireActivity())
                            .saveWord(mViewDataBinding.inpSearch.text.toString().trim())

                        if (!hasNextPage) {
                            homeSearchArrayList.clear()
                        }


                        data.docs.forEach {
                            if (mViewDataBinding.txtShops.isChecked) {
                                it.items = emptyList()
                            }
                            homeSearchArrayList.add(it)
                        }


//                            homeSearchArrayList.addAll(data.docs)
                        hatlyPopularAdapter.notifyDataSetChanged()
                        Log.d("homeSearchResponse", "onViewCreated: $data")

                        if (data.nextPage != null) {
                            nextPage = data.nextPage
                        }
                        hasNextPage = data.hasNextPage
                    }
                }

                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    if (isAdded) {

                        mViewDataBinding.root.snackbar(it.message!!)
                        Log.d("updatedWords", "onViewCreated: ${it.message!!}")
                    }
                }
            }
        }


        mViewDataBinding.recHomeSearch.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                currentItems = layoutManager2.childCount;
                totalItems = layoutManager2.itemCount;
                scrollOutItems = layoutManager2.findFirstVisibleItemPosition()

                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false;

                    if (hasNextPage) {
                        performSearch()
                    }
                }
            }
        })

    }

    private fun performSearch() {
        val search = mViewDataBinding.inpSearch.text.toString()
        mViewModel.homeSearch(search, categoryStr, typeStr, 10, nextPage)
    }

//    val modelProduct = productArrayList[position]
//    val bundle = Bundle()
//    bundle.putString("_id", modelProduct._id)
//    bundle.putString("name", modelProduct.name)
//    findNavController().navigate(
//    R.id.action_foodsShopHomeFragment_to_productPreviewFragment,
//    bundle
//    )


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
        categoryPosition = position

        when (categoryArray[position].message) {
            "All" -> {
                categoryStr = null
            }

            "Food" -> {
                categoryStr = "resturant"
            }

            "Grocery" -> {
                categoryStr = "grocery"
            }

            "Health & beauty" -> {
                categoryStr = "health"
            }

            "Home Business" -> {
                categoryStr = "home based"
            }
        }

        homeSearchTitleAdapter.notifyDataSetChanged()
        performSearch()
    }

    override fun clickRadioItem(shopClick: Int, prodClick: Int) {
        val modelProduct = homeSearchArrayList[shopClick].items[prodClick]
        val bundle = Bundle()
        bundle.putString("_id", modelProduct._id)
        bundle.putString("name", modelProduct.name)
        findNavController().navigate(
            R.id.action_homeSearchFragment_to_productPreviewFragment,
            bundle
        )
    }

    override fun clickCheckBoxItem(productClick: Int) {

    }

    override fun clickFreBoughtItem(shopClick: Int) {
        val modelShop = homeSearchArrayList[shopClick]
        val bundle = Bundle()
        if (modelShop.type == "resturant") {
            Log.d("modelShopsdsd", "clickFreBoughtItem: ${modelShop._id}")
            MART = Marts.FOOD
            bundle.putString("itemId", modelShop._id)
            findNavController().navigate(
                R.id.action_homeSearchFragment_to_foodsShopHomeFragment,
                bundle
            )
        } else {
            MART = if (modelShop.type == "home based") Marts.HOME_BUSINESS else if (modelShop.type == "health") Marts.HEALTH_BEAUTY else Marts.GROCERY
            bundle.putString("_id", modelShop._id)
            bundle.putString("name", modelShop.name)
            bundle.putString("address", modelShop.address.googleMapAddress)
            findNavController().navigate(
                R.id.action_homeSearchFragment_to_hatlyMartFragment,
                bundle
            )
        }
    }

    private fun extractShortAddress(fullAddress: String?): String? {
        val addressParts = fullAddress?.split(", ")
        val shortAddress = addressParts?.get(0)
        return shortAddress
    }

}