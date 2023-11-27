package com.teamx.hatlyUser.ui.fragments.products

import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.adapter.AddToCartInterface
import com.teamx.hatlyUser.ui.fragments.products.adapter.interfaces.ProductPreviewInterface
import com.teamx.hatlyUser.ui.fragments.products.adapter.frequentlyBought.RecommendedItemAdapter
import com.teamx.hatlyUser.ui.fragments.products.adapter.imageSlider.ImageSliderAdapter
import com.teamx.hatlyUser.ui.fragments.products.adapter.required.multiAdapter.MultiViewVariationRadioAdapter
import com.teamx.hatlyUser.ui.fragments.products.model.Recommended
import com.teamx.hatlyUser.ui.fragments.products.model.Veriation
import com.teamx.hatlyUser.utils.DialogHelperClass
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import java.util.Stack
import java.util.Timer
import java.util.TimerTask
import kotlin.random.Random


@AndroidEntryPoint
class ProductPreviewFragment :
    BaseFragment<com.teamx.hatlyUser.databinding.FragmentProductPreviewBinding, ProductPreviewViewModel>(),
    ProductPreviewInterface, DialogHelperClass.Companion.MultiProduct, AddToCartInterface {

    override val layoutId: Int
        get() = R.layout.fragment_product_preview
    override val viewModel: Class<ProductPreviewViewModel>
        get() = ProductPreviewViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    //    lateinit var variationArray: ArrayList<String>
    lateinit var variationArray: ArrayList<com.teamx.hatlyUser.ui.fragments.products.modelAddToCart.Veriation>
    lateinit var veriationArraylist: ArrayList<Veriation>

    //    lateinit var requiredveriationArrayList: ArrayList<Veriation>
//    lateinit var optionalArrayList: ArrayList<OptionalVeriaton>
    lateinit var freBoughtArrayList: ArrayList<Recommended>
    lateinit var imageSliderArray: ArrayList<String>

    //    lateinit var prodVariationRadio: ProductVariationRequiredAdapter
//    lateinit var prodOptionalVarAdapter: ProductVariationOptionalAdapter
    lateinit var multiViewVariationRadioAdapter: MultiViewVariationRadioAdapter
    lateinit var recommendedItemAdapter: RecommendedItemAdapter
    lateinit var imageSliderAdapter: ImageSliderAdapter

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
                "null"
            }
        }

        mViewDataBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        variationArray = ArrayList()
        veriationArraylist = ArrayList()
//        requiredveriationArrayList = ArrayList()
//        optionalArrayList = ArrayList()
        freBoughtArrayList = ArrayList()
        imageSliderArray = ArrayList()

//        prodVariationRadio = ProductVariationRequiredAdapter(veriationArraylist, this)
//        val requiredLayoutManager =
//            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
//        mViewDataBinding.recVarRequired.layoutManager = requiredLayoutManager
//        mViewDataBinding.recVarRequired.adapter = prodVariationRadio

//        prodOptionalVarAdapter = ProductVariationOptionalAdapter(optionalArrayList, this)
//        val optionalLayoutManager =
//            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
//        mViewDataBinding.layoutOptional.recOpt.layoutManager = optionalLayoutManager
//        mViewDataBinding.layoutOptional.recOpt.adapter = prodOptionalVarAdapter


        multiViewVariationRadioAdapter = MultiViewVariationRadioAdapter(veriationArraylist, this)
        val optionalLayoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.recVarRequired.layoutManager = optionalLayoutManager
        mViewDataBinding.recVarRequired.adapter = multiViewVariationRadioAdapter



        recommendedItemAdapter = RecommendedItemAdapter(freBoughtArrayList, this, this)
        val productLayoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.recFreBought.layoutManager = productLayoutManager
        mViewDataBinding.recFreBought.adapter = recommendedItemAdapter


        Log.d("storeId", "storeId: ${storeId}")
//        mViewModel.prodPreview(storeId)

//        storeId = "65007af747acc3f2a42d1581" //simple
//        storeId = "651a9a40f6fbdb97eebe34df" //veriable

        if (!mViewModel.prodPreviewResponse.hasActiveObservers()) {
            mViewModel.prodPreview(storeId)
        }
        mViewModel.prodPreviewResponse.observe(requireActivity()) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }

                Resource.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    it.data?.let { data ->

                        imageSliderArray.addAll(data.product.images)
                        imageSliderArray.add("http://31.220.17.28:8000/a7d941e85050bd8b4ed415dd553ce890.png")
                        imageSliderArray.add("http://31.220.17.28:8000/c151469af1659b3427f56e7a216800b0.png")
                        imageSliderArray.add("http://31.220.17.28:8000/a7d941e85050bd8b4ed415dd553ce890.png")
                        imageSliderAdapter.notifyDataSetChanged()
//                        Picasso.get().load(data.product.images[0]).into(mViewDataBinding.imgShop)

                        mViewDataBinding.textView22.text = try {
                            data.product.name
                        } catch (e: Exception) {
                            "null"
                        }
                        mViewDataBinding.textView23.text = try {
                            data.product.description
                        } catch (e: Exception) {
                            "null"
                        }

                        Log.d("frequentlyBought21", "onViewCreated: ${data.recommended}")

                        if (data.recommended?.isNotEmpty() == true) {
                            freBoughtArrayList.clear()
                            freBoughtArrayList.addAll(data.recommended)
                            mViewDataBinding.textView31.visibility = View.VISIBLE
                            mViewDataBinding.recFreBought.visibility = View.VISIBLE
                            recommendedItemAdapter.notifyDataSetChanged()
                        }

                        if (data.product.productType == "simple") {
                            Log.d("productType", "onViewCreated: Simple")

                            if (data.product.salePrice != 0.0) {
                                mViewDataBinding.textView24.text = try {
                                    "${data.product.salePrice} Aed"
                                } catch (e: Exception) {
                                    "null"
                                }
                                mViewDataBinding.textView25.paintFlags =
                                    mViewDataBinding.textView25.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                            } else {
                                mViewDataBinding.textView24.text = ""
                            }

                            mViewDataBinding.textView25.text = try {
                                "${data.product.price} Aed"
                            } catch (e: Exception) {
                                "null"
                            }

//                            mViewDataBinding.textView25.visibility = View.GONE
                            return@observe
                        }

                        Log.d("productType", "onViewCreated: working")

                        mViewDataBinding.textView24.text = try {
                            "${data.product.minPrice} Aed"
                        } catch (e: Exception) {
                            "null"
                        }

                        mViewDataBinding.textView25.text = try {
                            "${data.product.maxPrice} Aed"
                        } catch (e: Exception) {
                            "null"
                        }

                        if (data.product.veriations?.isNotEmpty() == true) {
                            mViewDataBinding.recVarRequired.visibility = View.VISIBLE

                            veriationArraylist.clear()
                            variationArray.clear()

                            veriationArraylist.addAll(data.product.veriations)

                            veriationArraylist.forEachIndexed { index, shopVeriation ->
                                veriationArraylist[index].selectedIndex = -1

                                val options: ArrayList<String> = ArrayList()

                                veriationArraylist[index].options.forEach { _ ->
                                    options.add("")
                                }

                                variationArray.add(
                                    com.teamx.hatlyUser.ui.fragments.products.modelAddToCart.Veriation(
                                        "",
                                        options
                                    )
                                )
//                                clickRadioItem(index, 0)
                            }
                            multiViewVariationRadioAdapter.notifyDataSetChanged()
                        }


//                        if (data.product.veriations?.isNotEmpty() == true) {
//                            requiredveriationArrayList.addAll(data.product.veriations)
//                        }

//                        mViewDataBinding.textView24.text = try {
//                            actualPrize(requiredveriationArrayList).toString()
//                        }catch (e:Exception){
//                            "0.0"
//                        }

//                        prodVariationRadio.notifyDataSetChanged()

//                        if (data.product.optionalVeriatons?.isNotEmpty() == true) {
////                            data.product.optionalVeriatons[0].isSelected = true
//                            mViewDataBinding.layoutOptional.mainOptionaLayout.visibility =
//                                View.VISIBLE
//                            optionalArrayList.addAll(data.product.optionalVeriatons)
//                            prodOptionalVarAdapter.notifyDataSetChanged()
//                        }


                    }
                }

                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    if (isAdded) {

                        mViewDataBinding.mainLayout.snackbar(it.message!!)
                    }
                }
            }
        }


        if (!mViewModel.addToCartResponse.hasActiveObservers()) {
            mViewModel.addToCartResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            if (data.success) {
                                if (isAdded) {

                                    mViewDataBinding.mainLayout.snackbar("Added")
                                }
                            }
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        if (isAdded) {

                            if (it.message == "Can not add products from multiple shops") {
                                DialogHelperClass.MultiProductDialog(requireContext(), this)
                                Log.d("addToCartResponse", "addToCart: ${it.message!!}")
                            }
                        }
                    }
                }
            }
        }


        if (!mViewModel.emptyCartResponse.hasActiveObservers()) {
            mViewModel.emptyCartResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            mViewDataBinding.mainLayout.snackbar("Cart is empty now you can add product")
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        if (isAdded) {

                            mViewDataBinding.mainLayout.snackbar("${it.message!!}")
                            Log.d("addToCartResponse", "addToCart: ${it.message!!}")
                        }
                    }
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
            if (mViewDataBinding.inpSpecialInstr.isVisible) {
                mViewDataBinding.inpSpecialInstr.visibility = View.GONE
            } else {
                mViewDataBinding.inpSpecialInstr.visibility = View.VISIBLE
            }
        }

        mViewDataBinding.textView30.setOnClickListener {

            val filteredVariations = variationArray.map { variation ->
                com.teamx.hatlyUser.ui.fragments.products.modelAddToCart.Veriation(
                    _id = variation._id,
                    options = variation.options.filter { it.isNotBlank() } as ArrayList<String>
                )
            }.filter { it.options.isNotEmpty() }

            Log.d("filteredVariations", "onViewCreated: $filteredVariations")

            val spInst = mViewDataBinding.inpSpecialInstr.text.toString()
            val params = JsonObject()
            try {
                params.addProperty("id", storeId)
                params.addProperty("quantity", quantityActualValue)
                if (filteredVariations.isNotEmpty()) {
                    params.add("veriations", Gson().toJsonTree(filteredVariations))
                }
                if (spInst.isNotEmpty()) {
                    params.addProperty("specialInstruction", spInst)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            mViewModel.addToCart(params)
        }


//        viewPager = findViewById(R.id.viewPager)

        // Replace imageResIds with your actual image resources

        imageSliderAdapter = ImageSliderAdapter(imageSliderArray)
//        imageSliderAdapter = ImageSliderAdapter(imageResIds)
        mViewDataBinding.viewPager.adapter = imageSliderAdapter

        timer = Timer()
        timer.scheduleAtFixedRate(AutoSlideTask(), 2000, 3000)

    }

    fun numGenerate(): Int {
        return Random.nextInt(3)
    }


    override fun clickRadioItem(requiredVarBox: Int, radioProperties: Int) {

        val veriationId = veriationArraylist[requiredVarBox]._id
        val optionId = veriationArraylist[requiredVarBox].options[radioProperties]._id

        variationArray[requiredVarBox]._id = veriationId

        if (!veriationArraylist[requiredVarBox].isMultiple) {
            veriationArraylist[requiredVarBox].selectedIndex = radioProperties
            multiViewVariationRadioAdapter.notifyItemChanged(requiredVarBox)
            multiViewVariationRadioAdapter.notifyItemRangeChanged(
                requiredVarBox,
                veriationArraylist.size
            )

            variationArray[requiredVarBox].options[0] = optionId

        } else {
            variationArray[requiredVarBox].options[radioProperties] =
                if (variationArray[requiredVarBox].options.contains(optionId)) "" else optionId
        }


//        val filteredVariations = variationArray.map { variation ->
//            com.teamx.hatlyUser.ui.fragments.products.modelAddToCart.Veriation(
//                _id = variation._id,
//                options = variation.options.filter { it.isNotBlank() } as ArrayList<String>
//            )
//        }.filter { it.options.isNotEmpty() }


//        val resultTitle = variationArray.joinToString("/")


//        Log.d("clickCategoryItem", "variationArray: $variationArray")
//        Log.d("clickCategoryItem", "filteredVariations: $filteredVariations")

//        mViewDataBinding.textView24.text = try {
//            actualPrize(requiredveriationArrayList, resultTitle).toString()
//        } catch (e: Exception) {
//            "0.0"
//        }
//        mViewDataBinding.textView25.visibility = View.GONE
    }

    override fun clickCheckBoxItem(optionalVeriation: Int) {

    }

    override fun clickFreBoughtItem(position: Int) {
        val modelRecommend = freBoughtArrayList[position]
        mViewModel.prodPreview(modelRecommend._id)
    }


    private fun quantityValue(quantity: Int) {
        if (quantity < 1) {
            return
        }
        quantityActualValue = quantity
        mViewDataBinding.textView29.text = quantityActualValue.toString()
    }

    private lateinit var viewPager: ViewPager2
    private var currentPage = 0
    private lateinit var timer: Timer
    private val handler = Handler()

    private inner class AutoSlideTask : TimerTask() {
        override fun run() {
            handler.post {
                if (currentPage == mViewDataBinding.viewPager.adapter?.itemCount?.minus(1)) {
                    currentPage = 0
                } else {
                    currentPage++
                }
                mViewDataBinding.viewPager.setCurrentItem(currentPage, true)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    override fun prodRemove() {
        mViewModel.emptyCart()
    }

    override fun addProduct(position: Int) {
        val prodModel = freBoughtArrayList[position]
        if (prodModel.productType == "simple") {
            val params = JsonObject()
            try {
                params.addProperty("id", prodModel._id)
                params.addProperty("quantity", 1)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            mViewModel.addToCart(params)
        }
    }

    private val debounceDelayMillis = 1000 // Set your desired debounce delay in milliseconds
    private val handlerQty = Handler(Looper.getMainLooper())
    private val actionStack = Stack<Int>()
    override fun updateQuantity(position: Int, quantity: Int) {
        handlerQty.removeCallbacksAndMessages(null)

        if (quantity > 0) {

            if (!actionStack.contains(position)) {
                actionStack.push(position)
            }
            freBoughtArrayList[position].cartQuantity = quantity
            recommendedItemAdapter.notifyItemChanged(position)

            updateQtyResponse()

        }
    }

    private fun updateQtyResponse() {

        val params = JsonObject()

        handlerQty.postDelayed({
            if (actionStack.isNotEmpty()) {
                val cartmodel = freBoughtArrayList[actionStack.pop()]
                params.addProperty("id", cartmodel.cartItemId)
                params.addProperty("quantity", cartmodel.cartQuantity)
                mViewModel.updateCartItem(params)

                mViewModel.updateCartItemResponse.observe(requireActivity()) {
                    when (it.status) {
                        Resource.Status.LOADING -> {
                            loadingDialog.show()
                        }

                        Resource.Status.SUCCESS -> {
                            loadingDialog.dismiss()
                            it.data?.let { data ->

                                if (actionStack.isNotEmpty()) {
                                    val cartmodel1 = freBoughtArrayList[actionStack.pop()]
                                    params.addProperty("id", cartmodel1.cartItemId)
                                    params.addProperty("quantity", cartmodel1.cartQuantity)
                                    mViewModel.updateCartItem(params)
                                } else {
//                                    layoutUpdate(data)
                                }
                            }
                            mViewModel.updateCartItemResponse.removeObservers(viewLifecycleOwner)
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

        }, debounceDelayMillis.toLong())
    }


}