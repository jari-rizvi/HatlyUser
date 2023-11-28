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
            if (cartProductArrayList.isNotEmpty()) {
                val addNote = mViewDataBinding.inpSpecialInstr.text.toString()
                val bundle = Bundle()
                bundle.putString("addNote", addNote)
                findNavController().navigate(R.id.action_cartFragment_to_checkOutFragment, bundle)
            } else {
                if (isAdded) {
                    mViewDataBinding.root.snackbar("Your cart is empty")
                }
            }
        }

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("handleOnBackPressed", "handleOnBackPressed: back")
                findNavController().popBackStack()
//                findNavController().popBackStack(R.id.homeFragment, false)
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
                    if (isAdded) {

                        mViewDataBinding.root.snackbar(it.message!!)
                    }
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

            if (!actionStack.contains(position)) {
                actionStack.push(position)
            }
            cartProductArrayList[position].quantity = quantity
            cartAdapter.notifyItemChanged(position)

            updateQtyResponse()

        }
    }

    private val debounceDelayMillis = 1000 // Set your desired debounce delay in milliseconds
    private val handler = Handler(Looper.getMainLooper())
    private val actionStack = Stack<Int>()


    private fun updateQtyResponse() {

        val params = JsonObject()

        handler.postDelayed({
            if (actionStack.isNotEmpty()) {
                val cartmodel = cartProductArrayList[actionStack.pop()]
                params.addProperty("id", cartmodel._id)
                params.addProperty("quantity", cartmodel.quantity)
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
                                    val cartmodel1 = cartProductArrayList[actionStack.pop()]
                                    params.addProperty("id", cartmodel1._id)
                                    params.addProperty("quantity", cartmodel1.quantity)
                                    mViewModel.updateCartItem(params)
                                } else {
                                    layoutUpdate(data)
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

    private fun layoutUpdate(data: ModelCart) {
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
        removeCartResponse(cartModel, position)
    }

    private fun removeCartResponse(cartModel: Product, position: Int) {
        mViewModel.removeCartItem(cartModel._id)

        mViewModel.removeCartItemResponse.observe(requireActivity()) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }

                Resource.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    it.data?.let { data ->

                        if (cartProductArrayList.isNotEmpty()) {

                            cartProductArrayList.removeAt(position)
                            cartAdapter.notifyItemRemoved(position)
                            cartAdapter.notifyItemRangeChanged(
                                position,
                                cartProductArrayList.size - position
                            )
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