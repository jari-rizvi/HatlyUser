package com.teamx.hatlyUser.ui.fragments.payments.cart

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
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentCartBinding
import com.teamx.hatlyUser.ui.fragments.payments.cart.adapter.CartAdapter
import com.teamx.hatlyUser.ui.fragments.payments.cart.interfaces.CartInterface
import com.teamx.hatlyUser.ui.fragments.payments.cart.model.ModelCart
import com.teamx.hatlyUser.ui.fragments.payments.cart.model.Product
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import java.util.Stack


@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding, CartViewModel>(), CartInterface {

    override val layoutId: Int
        get() = R.layout.fragment_cart
    override val viewModel: Class<CartViewModel>
        get() = CartViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    lateinit var cartProductArrayList: ArrayList<Product>
    lateinit var layoutManager2: LinearLayoutManager
    lateinit var cartAdapter: CartAdapter

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

        cartProductArrayList = ArrayList()

        mViewDataBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        mViewDataBinding.txtLogin.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_checkOutFragment)
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


        layoutManager2 = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        mViewDataBinding.recCart.layoutManager = layoutManager2

        mViewModel.getCart()

        mViewModel.getCartResponse.observe(requireActivity()) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }

                Resource.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    it.data?.let { data ->

//                        mViewDataBinding.textView212.text = try {
//                            "${data.subTotal} Aed"
//                        } catch (e: Exception) {
//                            "0.0 Aed"
//                        }
//
//                        mViewDataBinding.textView2123.text = try {
//                            "${data.tax} Aed"
//                        } catch (e: Exception) {
//                            "0.0 Aed"
//                        }
//
//                        mViewDataBinding.textView2144.text = try {
//                            "${data.total} Aed"
//                        } catch (e: Exception) {
//                            "0.0 Aed"
//                        }
//
//                        cartProductArrayList.clear()
//                        data.products?.let { it1 -> cartProductArrayList.addAll(it1) }
//                        cartAdapter.notifyDataSetChanged()
                        layoutUpdate(data)

                    }
                }

                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    mViewDataBinding.root.snackbar(it.message!!)
                }
            }
        }



        cartAdapter = CartAdapter(cartProductArrayList, this)
        mViewDataBinding.recCart.adapter = cartAdapter
        mViewDataBinding.recCart.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
                    isScrolling = false
//                    fetchData()
                }
            }
        })

    }

    override fun updateQuantity(position: Int, quantity: Int) {
        handler.removeCallbacksAndMessages(null)
        if (quantity > 0) {
            val cartModel = cartProductArrayList[position]
            cartProductArrayList[position].quantity = quantity
            cartAdapter.notifyItemChanged(position)



            updateQtyResponse(quantity, cartModel.id)

        }
    }

    private val debounceDelayMillis = 1000 // Set your desired debounce delay in milliseconds
    private val handler = Handler(Looper.getMainLooper())
    private val actionStack = Stack<UpdateQuantity>()

    private data class UpdateQuantity(val _id: String, val qty: Int)

    private fun updateQtyResponse(qty: Int, _id: String) {

        val params = JsonObject()
//        try {
//            params.addProperty("id", _id)
//            params.addProperty("quantity", qty)
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }

        actionStack.forEach {
            if (it._id == _id) {
                actionStack.remove(it)
                return@forEach
            }
        }
        actionStack.add(UpdateQuantity(_id, qty))



        Log.d("actionStack", "updateQtyResponse: $actionStack")

        handler.postDelayed({
            if (actionStack.isNotEmpty()) {
                val newParam = actionStack.pop()
                params.addProperty("id", newParam._id)
                params.addProperty("quantity", newParam.qty)

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
                                    val newParam = actionStack.pop()
                                    params.addProperty("id", newParam._id)
                                    params.addProperty("quantity", newParam.qty)
                                    mViewModel.updateCartItem(params)
                                } else {
                                    layoutUpdate(data)
                                }
                            }
                            mViewModel.updateCartItemResponse.removeObservers(viewLifecycleOwner)
                        }

                        Resource.Status.ERROR -> {
                            loadingDialog.dismiss()
                            mViewDataBinding.root.snackbar(it.message!!)
                        }
                    }
                }
            }

        }, debounceDelayMillis.toLong())

//        mViewModel.updateCartItem(params)


    }

    fun layoutUpdate(data: ModelCart) {
        mViewDataBinding.textView212.text = try {
            "${data.subTotal} Aed"
        } catch (e: Exception) {
            "0.0 Aed"
        }

        mViewDataBinding.textView2123.text = try {
            "${data.tax} Aed"
        } catch (e: Exception) {
            "0.0 Aed"
        }

        mViewDataBinding.textView2144.text = try {
            "${data.total} Aed"
        } catch (e: Exception) {
            "0.0 Aed"
        }

        cartProductArrayList.clear()
        data.products?.let { it1 -> cartProductArrayList.addAll(it1) }
        cartAdapter.notifyDataSetChanged()
    }

    override fun removeCartItem(position: Int) {
        val cartModel = cartProductArrayList[position]
        removeCartResponse(cartModel.id, position)
    }

    private fun removeCartResponse(_id: String, position: Int) {
        mViewModel.removeCartItem(_id)

        mViewModel.removeCartItemResponse.observe(requireActivity()) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }

                Resource.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    it.data?.let { data ->

                        if (data.success) {
                            if (cartProductArrayList.isNotEmpty()) {
                                cartProductArrayList.removeAt(position)
                                cartAdapter.notifyItemRemoved(position)
                                cartAdapter.notifyItemRangeRemoved(
                                    position,
                                    cartProductArrayList.size
                                )
                            }
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

//    private fun fetchData() {
//        mViewDataBinding.spinKit.visibility = View.VISIBLE
//        Handler(Looper.getMainLooper()).postDelayed({
//            for (i in 1..5) {
//                itemClasses.add("")
//                itemClasses.add("")
//                itemClasses.add("")
//                itemClasses.add("")
//                itemClasses.add("")
//                itemClasses.add("")
//                itemClasses.add("")
//                itemClasses.add("")
//                itemClasses.add("")
//                itemClasses.add("")
//                itemClasses.add("")
//            }
//            mViewDataBinding.spinKit.visibility = View.GONE
//            cartAdapter.notifyDataSetChanged()
//        }, 5000)
//    }


}