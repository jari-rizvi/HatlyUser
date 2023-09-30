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
import com.squareup.picasso.Picasso
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.databinding.FragmentOrderDetailBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.interfaces.HatlyShopInterface
import com.teamx.hatlyUser.ui.fragments.profile.orderdetail.adapter.DialogUplodeImageAdapter
import com.teamx.hatlyUser.ui.fragments.profile.orderdetail.adapter.OrderDetailAdapter
import com.teamx.hatlyUser.ui.fragments.profile.orderhistory.model.Product
import com.teamx.hatlyUser.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint
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

    lateinit var layoutManager1: LinearLayoutManager
    lateinit var orderDetailAdapter: OrderDetailAdapter
    lateinit var productOrderHistoryList: ArrayList<Product>

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
            Log.d("sharedViewModel", "onViewCreated: ${data}")

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



            Picasso.get().load(data.shop.image).into(mViewDataBinding.imgShop)

            productOrderHistoryList.addAll(data.products)
            orderDetailAdapter.notifyDataSetChanged()
        }

//        mViewDataBinding.recLocations.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
//                    isScrolling = true
//                }
//            }
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//                currentItems = layoutManager1.childCount
//                totalItems = layoutManager1.itemCount
//                scrollOutItems = layoutManager1.findFirstVisibleItemPosition()
//
//                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
//                    isScrolling = false
////                    fetchData()
//                }
//            }
//        })

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
        findNavController().navigate(R.id.action_orderDetailFragment_to_reviewSubmitedFragment)
    }

    override fun onCancel() {
        Log.d("reviewDialog", "onSubmit: onCancel")
    }

    lateinit var uploadImageArrayList : ArrayList<Uri>
    lateinit var dialogUplodeImageAdapter : DialogUplodeImageAdapter
    lateinit var recDialogBox  : RecyclerView
    lateinit var constraintLayout4  : ConstraintLayout

    private fun reviewDialog(): Dialog {
        val dialog = Dialog(requireActivity())
        dialog.setContentView(R.layout.permission_review_dialog)

        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        val imageView23 = dialog.findViewById<ImageView>(R.id.imageView23)

        val userDescription = dialog.findViewById<EditText>(R.id.userDescription)

        val materialRatingBar = dialog.findViewById<RatingBar>(R.id.materialRatingBar)
        constraintLayout4 = dialog.findViewById<ConstraintLayout>(R.id.constraintLayout4)

        recDialogBox = dialog.findViewById(R.id.recDialogBox)
        uploadImageArrayList = ArrayList()

        val categoryLayoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        dialogUplodeImageAdapter  = DialogUplodeImageAdapter(uploadImageArrayList, this)
        recDialogBox.layoutManager = categoryLayoutManager
        recDialogBox.adapter = dialogUplodeImageAdapter

        val txtLogin = dialog.findViewById<TextView>(R.id.txtLogin)

        imageView23.setOnClickListener {
            onCancel()
            dialog.dismiss()
        }

        constraintLayout4.setOnClickListener {
//            startForResult.launch("image/*")
            pickImagesLauncher.launch("image/*")
        }

        txtLogin.setOnClickListener {
            dialog.dismiss()
            onSubmit(userDescription.text.toString(), materialRatingBar.rating.toDouble())
        }

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

    private val pickImagesLauncher = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris: List<Uri>? ->
        // Handle the result here
        if (uris != null) {
            uploadImageArrayList.clear()
            for (uri in uris) {
                // Process each URI (image) as needed
                // Example: display the URI

                uploadImageArrayList.add(uri)
                Log.d("Image URI", uri.toString())
            }
            dialogUplodeImageAdapter.notifyDataSetChanged()

        }
        if (uploadImageArrayList.isNotEmpty()){
            constraintLayout4.visibility = View.GONE
            recDialogBox.visibility = View.VISIBLE
        }else{
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


}