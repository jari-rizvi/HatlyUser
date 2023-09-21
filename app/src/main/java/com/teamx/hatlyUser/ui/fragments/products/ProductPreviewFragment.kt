package com.teamx.hatlyUser.ui.fragments.products

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.ProductPreviewInterface
import com.teamx.hatlyUser.ui.fragments.products.adapter.optional.ProductVariationOptionalAdapter
import com.teamx.hatlyUser.ui.fragments.products.adapter.required.ProductVariationRequiredAdapter
import com.teamx.hatlyUser.ui.fragments.products.model.OptionalVeriaton
import com.teamx.hatlyUser.ui.fragments.products.model.ShopVeriation
import com.teamx.hatlyUser.ui.fragments.products.model.Veriation
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random


@AndroidEntryPoint
class ProductPreviewFragment :
    BaseFragment<com.teamx.hatlyUser.databinding.FragmentProductPreviewBinding, ProductPreviewViewModel>(),
    ProductPreviewInterface {

    override val layoutId: Int
        get() = R.layout.fragment_product_preview
    override val viewModel: Class<ProductPreviewViewModel>
        get() = ProductPreviewViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    lateinit var variationArray: ArrayList<String>
    lateinit var requiredArrayList: ArrayList<ShopVeriation>
    lateinit var requiredveriationArrayList: ArrayList<Veriation>
    lateinit var optionalArrayList: ArrayList<OptionalVeriaton>

    lateinit var prodVariationRadio: ProductVariationRequiredAdapter
    lateinit var prodOptionalVarAdapter: ProductVariationOptionalAdapter

    var storeId = ""
    var storeName = ""
    var storeAddress = ""

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
        if (bundle != null) {
            storeId = bundle.getString("_id", "")
            storeName = bundle.getString("name", "")
            mViewDataBinding.textView2.text = try {
                storeName
            }catch (e : Exception){
                ""
            }
        }

        mViewDataBinding.textView25.paintFlags =
            mViewDataBinding.textView25.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        variationArray = ArrayList()
        requiredArrayList = ArrayList()
        requiredveriationArrayList = ArrayList()
        optionalArrayList = ArrayList()

        prodVariationRadio = ProductVariationRequiredAdapter(requiredArrayList, this)
        val requiredLayoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.recVarRequired.layoutManager = requiredLayoutManager
        mViewDataBinding.recVarRequired.adapter = prodVariationRadio

        prodOptionalVarAdapter = ProductVariationOptionalAdapter(optionalArrayList, this)
        val optionalLayoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.layoutOptional.recOpt.layoutManager = optionalLayoutManager
        mViewDataBinding.layoutOptional.recOpt.adapter = prodOptionalVarAdapter

        mViewModel.prodPreview("64db9e0afb30bc9e9b456001")
//        mViewModel.prodPreview("64d243cfeccb23edb42b480a")

        mViewModel.prodPreviewResponse.observe(requireActivity()) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }

                Resource.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    it.data?.let { data ->
                        Picasso.get().load(data.product.images[0]).into(mViewDataBinding.imgShop)

                        mViewDataBinding.textView22.text = try {
                            data.product.name
                        } catch (e: Exception) {
                            ""
                        }
                        mViewDataBinding.textView23.text = try {
                            data.product.description
                        } catch (e: Exception) {
                            ""
                        }

                        if (data.product.productType == "simple"){
                            Log.d("productType", "onViewCreated: Simple")
                            mViewDataBinding.textView24.text = try {
                                "${data.product.prize} Aed"
                            } catch (e: Exception) {
                                ""
                            }
                            mViewDataBinding.textView25.visibility = View.GONE
                            return@observe
                        }

                        Log.d("productType", "onViewCreated: working")

                        mViewDataBinding.textView24.text = try {
                            "${data.product.minPrize} Aed"
                        } catch (e: Exception) {
                            ""
                        }

                        mViewDataBinding.textView25.text = try {
                            "${data.product.maxPrize} Aed"
                        }catch (e : Exception){
                            ""
                        }

                        if (data.product.shopVeriations?.isNotEmpty() == true) {
                            mViewDataBinding.recVarRequired.visibility = View.VISIBLE
                            requiredArrayList.addAll(data.product.shopVeriations)

                            requiredArrayList.forEachIndexed { index, shopVeriation ->
                                requiredArrayList[index].selectedIndex = -1
//                                clickRadioItem(index, 0)
                            }
                        }


                        if (data.product.veriations?.isNotEmpty() == true) {
                            requiredveriationArrayList.addAll(data.product.veriations)
                        }

//                        mViewDataBinding.textView24.text = try {
//                            actualPrize(requiredveriationArrayList).toString()
//                        }catch (e:Exception){
//                            "0.0"
//                        }

                        prodVariationRadio.notifyDataSetChanged()

                        if (data.product.optionalVeriatons?.isNotEmpty() == true) {
//                            data.product.optionalVeriatons[0].isSelected = true
                            mViewDataBinding.layoutOptional.mainOptionaLayout.visibility =
                                View.VISIBLE
                            optionalArrayList.addAll(data.product.optionalVeriatons)
                            prodOptionalVarAdapter.notifyDataSetChanged()
                        }


                    }
                }

                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    mViewDataBinding.root.snackbar(it.message!!)
                }
            }
        }

    }

    fun numGenerate(): Int {
        return Random.nextInt(3)
    }

    var resultTitle = ""

    override fun clickRadioItem(requiredVarBox: Int, radioProperties: Int) {
        requiredArrayList[requiredVarBox].selectedIndex = radioProperties

        prodVariationRadio.notifyItemChanged(requiredVarBox)
        prodVariationRadio.notifyItemRangeChanged(requiredVarBox, requiredArrayList.size)
//        prodVariationRadio.notifyDataSetChanged()

        Log.d(
            "clickCategoryItem",
            "clickCategoryItem: ${requiredArrayList[requiredVarBox].options[radioProperties]}"
        )

        val word = requiredArrayList[requiredVarBox].options[radioProperties]

        if (variationArray.isNotEmpty()){
            if (requiredVarBox >= variationArray.size || requiredVarBox < 0) {
                //index does not exists
                variationArray.add(requiredVarBox, word)
            } else {
                // index exists
                variationArray[requiredVarBox] = word
            }

            resultTitle = variationArray.joinToString("/")
        }else{
            variationArray.add(requiredVarBox, word)
        }



        Log.d("clickCategoryItem", "variationArray: $resultTitle")

        mViewDataBinding.textView24.text = try {
            actualPrize(requiredveriationArrayList).toString()
        } catch (e: Exception) {
            "0.0"
        }
    }

    override fun clickCheckBoxItem(optionalVeriation: Int) {
        optionalArrayList[optionalVeriation].isSelected =
            !optionalArrayList[optionalVeriation].isSelected
        Log.d(
            "clickCategoryItem",
            "variationArray: ${optionalArrayList[optionalVeriation].isSelected} ${optionalArrayList[optionalVeriation].name}"
        )
        prodOptionalVarAdapter.notifyItemChanged(optionalVeriation)
//        prodOptionalVarAdapter.notifyItemRangeChanged(optionalVeriation, optionalArrayList.size)
    }

    private fun actualPrize(variation: List<Veriation>): Double {
        variation.forEach {
            if (resultTitle.isNotEmpty()) {
                if (resultTitle == it.title) {
                    return it.prize
//                    Log.d("clickCategoryItem", "1")
//                    return@forEach
//                    Log.d("clickCategoryItem", "2")
                }
            }
        }
        return 0.0
    }

}