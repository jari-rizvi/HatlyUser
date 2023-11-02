package com.teamx.hatlyUser.ui.fragments.profile.orderdetail

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentOrderDetailBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.profile.orderdetail.adapter.DialogUplodeImageAdapter
import com.teamx.hatlyUser.ui.fragments.profile.orderdetail.adapter.OrderDetailAdapter
import com.teamx.hatlyUser.ui.fragments.profile.orderhistory.model.Product
import com.teamx.hatlyUser.utils.DialogHelperClass
import com.teamx.hatlyUser.utils.TestRet
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.zhanghai.android.materialratingbar.MaterialRatingBar
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONException
import java.io.File
import java.io.FileOutputStream


@AndroidEntryPoint
class OrderDetailFragment : BaseFragment<FragmentOrderDetailBinding, OrderDetailViewModel>(),
    DialogHelperClass.Companion.ReviewProduct, HatlyShopInterface {

    override val layoutId: Int
        get() = R.layout.fragment_order_detail
    override val viewModel: Class<OrderDetailViewModel>
        get() = OrderDetailViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var layoutManager1: LinearLayoutManager
    private lateinit var orderDetailAdapter: OrderDetailAdapter
    private lateinit var productOrderHistoryList: ArrayList<Product>

    //    private lateinit var uploadImageArrayList: ArrayList<File>
    private lateinit var dialogUplodeImageAdapter: DialogUplodeImageAdapter
    private lateinit var recDialogBox: RecyclerView
    private lateinit var constraintLayout4: ConstraintLayout

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

        mViewDataBinding.txtLogin1.setOnClickListener {
//            DialogHelperClass.reviewDialog(requireActivity(),startForResult,this)
            reviewDialog()
        }

        mViewDataBinding.txtLogin.setOnClickListener {

        }

        mViewDataBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        productOrderHistoryList = ArrayList()


        orderDetailAdapter = OrderDetailAdapter(productOrderHistoryList)
        mViewDataBinding.recLocations.adapter = orderDetailAdapter
        layoutManager1 = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.recLocations.layoutManager = layoutManager1


        sharedViewModel.orderHistory.observe(requireActivity()) { data ->

            if (data.isFromWallet) {
                mViewDataBinding.btnLayout.visibility = View.GONE
                mViewDataBinding.paidLayout.visibility = View.VISIBLE
            } else {
                mViewDataBinding.btnLayout.visibility = View.VISIBLE
                mViewDataBinding.paidLayout.visibility = View.GONE
            }

            when (data.orderType) {
                "CASH_ON_DELIVERY" -> {
                    mViewDataBinding.txtTitle114455.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.paid_with_cash,
                        0,
                        0,
                        0
                    );
                    mViewDataBinding.txtTitle114455.text = try {
                        "Cash"
                    } catch (e: Exception) {
                        ""
                    }
                }

                "ONLINE_PAYMENTS" -> {
                    mViewDataBinding.txtTitle114455.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.paid_with_card,
                        0,
                        0,
                        0
                    )
                    mViewDataBinding.txtTitle114455.text = try {
                        "Credit Card"
                    } catch (e: Exception) {
                        ""
                    }
                }
            }

            when (data.status) {
                "placed" -> {
                    mViewDataBinding.txtLogin.text = "Cancel Order"
                    mViewDataBinding.txtLogin.isChecked = true
                }

                "confirmed" -> {
                    mViewDataBinding.txtLogin.text = "Cancel Order"
                    mViewDataBinding.txtLogin.isChecked = false
                    mViewDataBinding.txtLogin.isEnabled = false
                    mViewDataBinding.txtLogin1.visibility = View.GONE
                }

                "delivered" -> {
                    mViewDataBinding.txtLogin.text = "Re-Order"
                    mViewDataBinding.txtLogin.isChecked = true
                    mViewDataBinding.txtLogin1.visibility = View.VISIBLE
                }

                "cancelled" -> {
                    mViewDataBinding.txtLogin.visibility = View.GONE
                    mViewDataBinding.txtLogin1.visibility = View.GONE
                }
            }

            mViewDataBinding.txtTitle.text = try {
                data.shop.name
            } catch (e: Exception) {
                ""
            }

            mViewDataBinding.txtTitle1141.text = try {
                "#${data._id.substring(0, 8)}"
            } catch (e: Exception) {
                ""
            }

            mViewDataBinding.txtAddress.text = try {
//                "${data.shop.address.googleMapAddress}"
                "${data.shippingAddress.floor} ${data.shippingAddress.building} ${data.shippingAddress.area} ${data.shippingAddress.streat}"
            } catch (e: Exception) {
                ""
            }

            mViewDataBinding.txtTitle11123.text = try {
                "${data.subTotal}"
            } catch (e: Exception) {
                ""
            }

            mViewDataBinding.txtTitle111323.text = try {
                "${data.deliveryCharges}"
            } catch (e: Exception) {
                ""
            }

            mViewDataBinding.txtTitle1113233.text = try {
                "${data.total}"
            } catch (e: Exception) {
                ""
            }

            Picasso.get().load(data.shop.image).resize(500, 500).into(mViewDataBinding.imgShop)

            productOrderHistoryList.addAll(data.products)
            orderDetailAdapter.notifyDataSetChanged()
        }

        if (!mViewModel.uploadReviewImgResponse.hasActiveObservers()) {
            mViewModel.uploadReviewImgResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            if (data.isNotEmpty()) {
                                val params = JsonObject()
                                try {
                                    params.addProperty(
                                        "shopId",
                                        sharedViewModel.orderHistory.value?.shop?._id ?: ""
                                    )
                                    val jsonArray =
                                        JsonArray() // Create a JsonArray to hold your list
                                    data.forEach { jsonArray.add(it) }
                                    params.add("images", jsonArray)
                                    params.addProperty("ratting", materialRatingBar.rating)
                                    params.addProperty(
                                        "description",
                                        userDescription.text.toString()
                                    )

                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }

                                mViewModel.reviewOrder(params)
                            }


                            Log.d("uploadImagesRes", "onViewCreated: $data")
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        Log.d("uploadImagesRes", "onViewCreated: ${it}")
                        if (isAdded) {
                            mViewDataBinding.root.snackbar(it.message!!)
                        }
                    }
                }
            }
        }

        mViewDataBinding.txtLogin.setOnClickListener {
            sharedViewModel.orderHistory.value?.let { it1 ->
                when (it1.status) {
                    "placed" -> {
                        mViewModel.cancelOrder(it1._id)
                    }

                    "confirmed" -> {
                    }

                    "delivered" -> {
                        mViewModel.reOrder(it1._id)
                    }

                    "cancelled" -> {
                    }
                }

            }
        }


        if (!mViewModel.reOrderResponse.hasActiveObservers()) {
            mViewModel.reOrderResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->

                            if (data.success) {
                                findNavController().navigate(R.id.action_orderDetailFragment_to_cartFragment)
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


        if (!mViewModel.reviewOrderResponse.hasActiveObservers()) {
            mViewModel.reviewOrderResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            if (data.name.isNotEmpty()) {
                                findNavController().navigate(R.id.action_orderDetailFragment_to_reviewSubmitedFragment)
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

        if (!mViewModel.cancelOrderResponse.hasActiveObservers()) {
            mViewModel.cancelOrderResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            if (data.success) {
                                sharedViewModel.orderHistory.value?.status = "cancelled"
                                mViewDataBinding.root.snackbar("Your order has been canceled")
                                findNavController().popBackStack()
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


    }

//    private fun fetchData() {
//        mViewDataBinding.spinKit.visibility = View.VISIBLE
//        Handler(Looper.getMainLooper()).postDelayed({
//            for (i in 1..5) {
//                productOrderHistoryList.add("")
//                productOrderHistoryList.add("")
//                productOrderHistoryList.add("")
//                productOrderHistoryList.add("")
//                productOrderHistoryList.add("")
//                productOrderHistoryList.add("")
//                productOrderHistoryList.add("")
//                productOrderHistoryList.add("")
//                productOrderHistoryList.add("")
//                productOrderHistoryList.add("")
//                productOrderHistoryList.add("")
//            }
//            mViewDataBinding.spinKit.visibility = View.GONE
//            orderDetailAdapter.notifyDataSetChanged()
//        }, 5000)
//    }


    override fun onSubmit(description: String, rating: Double) {
        Log.d("reviewDialog", "description: ${description} rating ${rating}")
//        findNavController().navigate(R.id.action_orderDetailFragment_to_reviewSubmitedFragment)
    }

    override fun onCancel() {
        Log.d("reviewDialog", "onSubmit: onCancel")
    }

    lateinit var materialRatingBar: RatingBar
    lateinit var userDescription: EditText

    private fun reviewDialog(): Dialog {
        val dialog = Dialog(requireActivity())
        dialog.setContentView(R.layout.permission_review_dialog)

        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        val imageView23 = dialog.findViewById<ImageView>(R.id.imageView23)

        userDescription = dialog.findViewById(R.id.userDescription)

        materialRatingBar = dialog.findViewById(R.id.materialRatingBar)
        constraintLayout4 = dialog.findViewById(R.id.constraintLayout4)

        recDialogBox = dialog.findViewById(R.id.recDialogBox)

        imageFiles = ArrayList()

        val categoryLayoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        dialogUplodeImageAdapter = DialogUplodeImageAdapter(imageFiles, this)
        recDialogBox.layoutManager = categoryLayoutManager
        recDialogBox.adapter = dialogUplodeImageAdapter

        val txtLogin = dialog.findViewById<TextView>(R.id.txtLogin)

        imageView23.setOnClickListener {
            onCancel()
            dialog.dismiss()
        }

        txtLogin.setOnClickListener {
            if (imageFiles.isNotEmpty() && imageFiles.size < 6) {
                Log.d("imageFiles", "reviewDialog: ${imageFiles[0].absolutePath}")
                dialog.dismiss()
                uploadWithRetrofit(imageFiles)
            }
        }

        constraintLayout4.setOnClickListener {
//            startForResult.launch("image/*")
            pickImagesLauncher.launch("image/*")
        }


//        txtLogin.setOnClickListener {
//            dialog.dismiss()
//            onSubmit(userDescription.text.toString(), materialRatingBar.rating.toDouble())
//        }

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()
        return dialog
    }


//    private val startForResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
//            uri?.let {
//                val str = "${requireContext().filesDir}/file.jpg"
//
//                Log.d("startForResult", "Profile image: $it")
//
//
////                uploadWithRetrofit(it)
//
//                val bitmap = MediaStore.Images.Media.getBitmap(
//                    requireActivity().contentResolver,
//                    uri
//                )
//
//// Compress the bitmap to a JPEG format with 80% quality and save it to a file
//                val outputStream = FileOutputStream(str)
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
//                outputStream.close()
//
//
//
//
////                Picasso.get().load(it).into(mViewDataBinding.profilePicture)
////
////                uploadWithRetrofit(File(str))
//            }
//
//
//        }

    private lateinit var imageFiles: ArrayList<File>

    private val pickImagesLauncher =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris: List<Uri>? ->
            // Handle the result here


            if (uris != null) {
                imageFiles.clear()

                uris.forEachIndexed { index, uri ->

                    val str = "${requireContext().filesDir}/$index.jpg"

                    val bitmap = MediaStore.Images.Media.getBitmap(
                        requireActivity().contentResolver,
                        uri
                    )


                    val outputStream = FileOutputStream(str)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
                    outputStream.close()

                    if (File(str).exists()) {
                        Log.d("uploadImageArrayList", "exist: $str")
                    }

                    imageFiles.add(File(str))

                }

                dialogUplodeImageAdapter.notifyDataSetChanged()
            }
            if (imageFiles.isNotEmpty()) {
                constraintLayout4.visibility = View.GONE
                recDialogBox.visibility = View.VISIBLE
            } else {
                constraintLayout4.visibility = View.VISIBLE
                recDialogBox.visibility = View.GONE
            }
        }

    override fun clickshopItem(position: Int) {

    }

    override fun clickCategoryItem(position: Int) {
        pickImagesLauncher.launch("image/*")
    }

    override fun clickMoreItem(position: Int) {

    }


    private fun uploadWithRetrofit(imageFiles: List<File>) {


        val imagesList = mutableListOf<MultipartBody.Part>()

        for (imageUri in imageFiles) {
            imagesList.add(prepareFilePart("images", imageUri))
        }


        mViewModel.uploadReviewImg(imagesList)

    }

    private fun prepareFilePart(partName: String, fileUri: File): MultipartBody.Part {
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), fileUri)
        return MultipartBody.Part.createFormData(partName, fileUri.name, requestFile)
    }

}