package com.teamx.hatlyUser.ui.fragments.products

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.navigation.navOptions
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.modelShopHome.Product
import com.teamx.hatlyUser.ui.fragments.products.hatly.ItemClass
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.products.model.ShopVeriation
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductPreviewFragment : BaseFragment<com.teamx.hatlyUser.databinding.FragmentProductPreviewBinding, ProductPreviewViewModel>(),
    HatlyShopInterface {

    override val layoutId: Int
        get() = R.layout.fragment_product_preview
    override val viewModel: Class<ProductPreviewViewModel>
        get() = ProductPreviewViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    lateinit var requiredArrayList: ArrayList<ShopVeriation>

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
        mViewDataBinding.textView25.paintFlags = mViewDataBinding.textView25.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        requiredArrayList = ArrayList()

        mViewModel.prodPreview("64db9e0afb30bc9e9b456001")

        mViewModel.prodPreviewResponse.observe(requireActivity()) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }

                Resource.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    it.data?.let { data ->

                        Picasso.get().load(data.product.images[0]).into(mViewDataBinding.imgShop)
                        mViewDataBinding.textView22.text=try {
                            data.product.name
                        }catch (e : Exception){
                            ""
                        }
                        mViewDataBinding.textView23.text=try {
                            data.product.description
                        }catch (e : Exception){
                            ""
                        }
                        mViewDataBinding.textView24.text=try {
                            data.product.minPrize.toString()
                        }catch (e : Exception){
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

//        val layoutManager1 = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

//        mViewDataBinding.recCategories.layoutManager = layoutManager1


//        val itemClasses: ArrayList<String> = ArrayList()

        val itemClasses1: ArrayList<ItemClass> = ArrayList()

//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")
//        itemClasses.add("")

//        itemClasses1.add(ItemClass(0,"Required 1"))
//        itemClasses1.add(ItemClass(0,"Required 2"))
//        itemClasses1.add(ItemClass(1,54,"Optional",""))
//        itemClasses1.add(ItemClass(1,54,"Optional",""))

//        val hatlyPopularAdapter = HatlyPopularAdapter(itemClasses,this)
//        val prodVariationRadio = ProductVariationRadioAdapter(itemClasses1)
//        mViewDataBinding.recCategories.adapter = hatlyPopularAdapter


//        val prodVariationRadio = MultiViewVariationRadioAdapter(itemClasses1)
//        val layoutManager2 = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
//        mViewDataBinding.recVariation1.layoutManager = layoutManager2
//        mViewDataBinding.recVariation1.adapter = prodVariationRadio


    }

    override fun clickshopItem(position: Int) {

    }

    override fun clickCategoryItem(position: Int) {

    }

    override fun clickMoreItem(position: Int) {

    }


}