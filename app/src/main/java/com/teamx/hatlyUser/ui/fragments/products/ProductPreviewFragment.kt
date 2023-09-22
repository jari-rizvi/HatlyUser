package com.teamx.hatlyUser.ui.fragments.products

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.ProductPreviewInterface
import com.teamx.hatlyUser.ui.fragments.products.adapter.frequentlyBought.FrequentlyBoughtAdapter
import com.teamx.hatlyUser.ui.fragments.products.adapter.optional.ProductVariationOptionalAdapter
import com.teamx.hatlyUser.ui.fragments.products.adapter.required.ProductVariationRequiredAdapter
import com.teamx.hatlyUser.ui.fragments.products.model.FrequentlyBought
import com.teamx.hatlyUser.ui.fragments.products.model.OptionalVeriaton
import com.teamx.hatlyUser.ui.fragments.products.model.ShopVeriation
import com.teamx.hatlyUser.ui.fragments.products.model.Veriation
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
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
    lateinit var freBoughtArrayList: ArrayList<FrequentlyBought>

    lateinit var prodVariationRadio: ProductVariationRequiredAdapter
    lateinit var prodOptionalVarAdapter: ProductVariationOptionalAdapter
    lateinit var frequentlyBoughtAdapter: FrequentlyBoughtAdapter

    var storeId = ""
    var storeName = ""
    var storeAddress = ""
    var quantityActualValue = 1

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
            } catch (e: Exception) {
                ""
            }
        }

        mViewDataBinding.textView25.paintFlags =
            mViewDataBinding.textView25.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        variationArray = ArrayList()
        requiredArrayList = ArrayList()
        requiredveriationArrayList = ArrayList()
        optionalArrayList = ArrayList()
        freBoughtArrayList = ArrayList()

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


        frequentlyBoughtAdapter = FrequentlyBoughtAdapter(freBoughtArrayList, this)
        val productLayoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.recFreBought.layoutManager = productLayoutManager
        mViewDataBinding.recFreBought.adapter = frequentlyBoughtAdapter


        mViewModel.prodPreview(storeId)
//        mViewModel.prodPreview("64db9e0afb30bc9e9b456001") // veriation
//        mViewModel.prodPreview("64d243cfeccb23edb42b480a") // simple

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

                        Log.d("frequentlyBought21", "onViewCreated: ${data.frequentlyBought}")

                        if (data.frequentlyBought?.isNotEmpty()   == true) {
                            freBoughtArrayList.addAll(data.frequentlyBought)
                            mViewDataBinding.textView31.visibility = View.VISIBLE
                            mViewDataBinding.recFreBought.visibility = View.VISIBLE
                            frequentlyBoughtAdapter.notifyDataSetChanged()
                        }

                        if (data.product.productType == "simple") {
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
                        } catch (e: Exception) {
                            ""
                        }

                        if (data.product.shopVeriations?.isNotEmpty() == true) {
                            mViewDataBinding.recVarRequired.visibility = View.VISIBLE
                            requiredArrayList.addAll(data.product.shopVeriations)

                            Log.d("requiredArrayList", "onViewCreated: ${requiredArrayList.size}")

                            requiredArrayList.forEachIndexed { index, shopVeriation ->
                                requiredArrayList[index].selectedIndex = -1
                                variationArray.add("")
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

        mViewModel.addToCartResponse.observe(requireActivity()) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }

                Resource.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    it.data?.let { data ->
                        if (data.success){
                            mViewDataBinding.root.snackbar("Added")
                        }
                    }
                }

                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    mViewDataBinding.root.snackbar(it.message!!)
                }
            }
        }

        mViewDataBinding.imgIncreament.setOnClickListener {
            quantityValue(quantityActualValue + 1)
        }

        mViewDataBinding.imgDereament.setOnClickListener {
            quantityValue(quantityActualValue - 1)
        }

        mViewDataBinding.txtInstructionClick.setOnClickListener {
            if (mViewDataBinding.inpSpecialInstr.isVisible){
                mViewDataBinding.inpSpecialInstr.visibility = View.GONE
            }else{
                mViewDataBinding.inpSpecialInstr.visibility = View.VISIBLE
            }
        }

        mViewDataBinding.textView30.setOnClickListener {
            val spInst = mViewDataBinding.inpSpecialInstr.text.toString()
            val params = JsonObject()
            try {
                params.addProperty("id", storeId)
                params.addProperty("quantity", quantityActualValue)
                if (spInst.isNotEmpty()){
                    params.addProperty("specialInstruction", spInst)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            mViewModel.addToCart(params)
        }

    }

    fun numGenerate(): Int {
        return Random.nextInt(3)
    }


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

        variationArray[requiredVarBox] = word

        val resultTitle = variationArray.joinToString("/")

//        if (variationArray.isNotEmpty()){
//            if (requiredVarBox >= variationArray.size || requiredVarBox < 0) {
//                //index does not exists
//                variationArray.add(requiredVarBox, word)
//            } else {
//                // index exists
//                variationArray[requiredVarBox] = word
//            }
//
//            resultTitle = variationArray.joinToString("/")
//        }else{
//            variationArray.add(requiredVarBox, word)
//        }


        Log.d("clickCategoryItem", "variationArray: $resultTitle")

        mViewDataBinding.textView24.text = try {
            actualPrize(requiredveriationArrayList, resultTitle).toString()
        } catch (e: Exception) {
            "0.0"
        }
        mViewDataBinding.textView25.visibility = View.GONE
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

    override fun clickFreBoughtItem(position: Int) {
    }
    private fun actualPrize(variation: List<Veriation>, resultTitle: String): Double {
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

    private fun quantityValue(quantity : Int){
        if (quantity < 1){
            return
        }
        quantityActualValue = quantity
        mViewDataBinding.textView29.text = quantityActualValue.toString()
    }

}