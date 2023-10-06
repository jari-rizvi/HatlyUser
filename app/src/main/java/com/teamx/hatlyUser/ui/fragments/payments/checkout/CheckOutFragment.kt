package com.teamx.hatlyUser.ui.fragments.payments.checkout

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.approve.OnApprove
import com.paypal.checkout.cancel.OnCancel
import com.paypal.checkout.config.CheckoutConfig
import com.paypal.checkout.config.Environment
import com.paypal.checkout.config.SettingsConfig
import com.paypal.checkout.createorder.CreateOrder
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.OrderIntent
import com.paypal.checkout.createorder.UserAction
import com.paypal.checkout.order.Amount
import com.paypal.checkout.order.AppContext
import com.paypal.checkout.order.OrderRequest
import com.paypal.checkout.order.PurchaseUnit
import com.paypal.checkout.paymentbutton.PaymentButtonContainer
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.BuildConfig
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentCheckOutBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.stores.model.Coordinates
import com.teamx.hatlyUser.ui.fragments.payments.checkout.adapter.CheckOutAdapter
import com.teamx.hatlyUser.ui.fragments.payments.checkout.model.Product
import com.teamx.hatlyUser.ui.fragments.payments.checkout.modelPlaceOrder.ShippingAddress
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException


@AndroidEntryPoint
class CheckOutFragment : BaseFragment<FragmentCheckOutBinding, CheckOutViewModel>(),
    OnMapReadyCallback {

    override val layoutId: Int
        get() = R.layout.fragment_check_out
    override val viewModel: Class<CheckOutViewModel>
        get() = CheckOutViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var cartProductArrayList: ArrayList<Product>

    private lateinit var hatlyPopularAdapter: CheckOutAdapter

    private var addNote = ""

    var mapFragment: SupportMapFragment? = null

    lateinit var paymentSheet: PaymentSheet

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

        if (isAdded) {
            paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
        }

        mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        val bundle = arguments
        if (bundle != null) {
            addNote = bundle.getString("addNote", "")
        }

        cartProductArrayList = ArrayList()

        mViewDataBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        mViewDataBinding.textView121653.setOnClickListener {
            findNavController().navigate(R.id.action_checkOutFragment_to_paymentMethodFragment)
        }

        mViewDataBinding.txtLogin.setOnClickListener {

            mViewModel.placeOrder(createOrderJsonObject())
        }

//        val onBackPressedCallback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                Log.d("handleOnBackPressed", "handleOnBackPressed: back")
//                findNavController().popBackStack(R.id.homeFragment,false)
//            }
//        }
//
//        requireActivity().onBackPressedDispatcher.addCallback(
//            viewLifecycleOwner,
//            onBackPressedCallback
//        )


        val layoutManager2 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        mViewDataBinding.recCheckout.layoutManager = layoutManager2

        hatlyPopularAdapter = CheckOutAdapter(cartProductArrayList)
        mViewDataBinding.recCheckout.adapter = hatlyPopularAdapter


//        24.90147393769095, 67.11531056779101
        val params = JsonObject()
        try {
            params.addProperty("lat", 24.90147393769095)
            params.addProperty("lng", 7.11531056779101)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        mViewDataBinding.swOnOff.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                val params = JsonObject()
                try {
                    params.add(
                        "coordinates",
                        Gson().toJsonTree(Coordinates(24.90147393769095, 24.90147393769095))
                    )
                    params.addProperty("useWallet", isChecked)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                mViewModel.orderSummary(params)
            }
        }

        mViewModel.checkout(params)

        mViewModel.checkoutResponse.observe(requireActivity()) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }

                Resource.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    it.data?.let { data ->

                        Log.d("checkoutResponse", "onViewCreated: ${data}")

                        mViewDataBinding.textView21343.text = try {
                            "${data.balance} Aed"
                        } catch (e: Exception) {
                            "0.0 Aed"
                        }
                        mViewDataBinding.textView2144633.text = try {
                            "${data.subTotal} Aed"
                        } catch (e: Exception) {
                            "0.0 Aed"
                        }

                        mViewDataBinding.textView214467433.text = try {
                            "${data.deliveryCharges} Aed"
                        } catch (e: Exception) {
                            "0.0 Aed"
                        }

                        mViewDataBinding.textView2144678433.text = try {
                            "${data.total} Aed"
                        } catch (e: Exception) {
                            "0.0 Aed"
                        }


                        cartProductArrayList.clear()
                        data.products?.let { it1 -> cartProductArrayList.addAll(it1) }
                        hatlyPopularAdapter.notifyDataSetChanged()
                    }
                }

                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    mViewDataBinding.root.snackbar(it.message!!)
                }
            }
        }

        mViewModel.orderSummaryResponse.observe(requireActivity()) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }

                Resource.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    it.data?.let { data ->

                        Log.d("checkoutResponse", "onViewCreated: ${data}")

                        mViewDataBinding.textView21343.text = try {
                            "${data.balance} Aed"
                        } catch (e: Exception) {
                            "0.0 Aed"
                        }
                        mViewDataBinding.textView2144633.text = try {
                            "${data.subTotal} Aed"
                        } catch (e: Exception) {
                            "0.0 Aed"
                        }

                        mViewDataBinding.textView214467433.text = try {
                            "${data.deliveryCharges} Aed"
                        } catch (e: Exception) {
                            "0.0 Aed"
                        }

                        mViewDataBinding.textView2144678433.text = try {
                            "${data.total} Aed"
                        } catch (e: Exception) {
                            "0.0 Aed"
                        }


                        cartProductArrayList.clear()
                        data.products?.let { it1 -> cartProductArrayList.addAll(it1) }
                        hatlyPopularAdapter.notifyDataSetChanged()
                    }
                }

                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    mViewDataBinding.swOnOff.isChecked = false
                    mViewDataBinding.root.snackbar(it.message!!)
                }
            }
        }

        mViewModel.placeOrderResponse.observe(requireActivity()) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }

                Resource.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    it.data?.let { data ->
                        if (data.clientSecret != null) {
                            Log.d(
                                "placeOrderResponse",
                                "onViewCreated: stripe payment ${data.clientSecret}"
                            )
                            showPaypal()
//                            showStripeSheet(data.clientSecret)
                            return@observe
                        }
                        if (data.status == "placed") {
                            Log.d("placeOrderResponse", "onViewCreated: without stripe payment")
                            findNavController().navigate(R.id.action_checkOutFragment_to_orderPlacedFragment)
                        }
                    }
                }

                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    mViewDataBinding.root.snackbar(it.message!!)
                }
            }
        }

        val YOUR_CLIENT_ID = "AZX5jqRs5Xi5XZacM1LBdmAqSzCRWslUa7Ic-vPu2bvHnzbePURxcYBSTl60fd6b5ga8djAajpRSYfVs"
        val config = CheckoutConfig(
            application = requireActivity().application,
            clientId = YOUR_CLIENT_ID,
            environment = Environment.SANDBOX,
            returnUrl = "${BuildConfig.APPLICATION_ID}://paypalpay",
//            currencyCode = CurrencyCode.USD,
            userAction = UserAction.PAY_NOW,
            settingsConfig = SettingsConfig(
                loggingEnabled = false,
                showWebCheckout = true
            )
        )
        PayPalCheckout.setConfig(config)
    }

    //    var paymentButtonContainer: PaymentButtonContainer? = null
    private fun showPaypal() {

        PayPalCheckout.start(CreateOrder { createOrderActions ->
            val order =
                OrderRequest(
                    intent = OrderIntent.CAPTURE,
                    appContext = AppContext(userAction = UserAction.PAY_NOW),
                    purchaseUnitList =
                    listOf(
                        PurchaseUnit(
                            amount =
                            Amount(currencyCode = CurrencyCode.USD, value = "10.00")
                        )
                    )
                )
            createOrderActions.create(order) {res ->
                Log.d("createOrderActions", "OrderId: approve ${res}")
            }
        }, onApprove = OnApprove { approval ->
            Log.d("createOrderActions", "OrderId: approve ${approval.data.orderId}")
        }, onCancel = OnCancel.invoke {
            Log.d("createOrderActions", "OrderId: cancel")
        })

//        PayPalCheckout.registerCallbacks(onApprove = OnApprove{
//
//        }, on)

//        PayPalCheckout.startCheckout(CreateOrder { createOrderActions ->
//            val order =
//                OrderRequest(
//                    intent = OrderIntent.CAPTURE,
//                    appContext = AppContext(userAction = UserAction.PAY_NOW),
//                    purchaseUnitList =
//                    listOf(
//                        PurchaseUnit(
//                            amount =
//                            Amount(currencyCode = CurrencyCode.USD, value = "10.00")
//                        )
//                    )
//                )
//            createOrderActions.create(order) {res ->
//                Log.d("createOrderActions", "OrderId: approve ${res}")
//            }
//        })

//        PayPalCheckout.startCheckout(CreateOrder { createOrderActions ->
//            val order =
//                OrderRequest(
//                    intent = OrderIntent.CAPTURE,
//                    appContext = AppContext(userAction = UserAction.PAY_NOW),
//                    purchaseUnitList =
//                    listOf(
//                        PurchaseUnit(
//                            amount =
//                            Amount(currencyCode = CurrencyCode.USD, value = "10.00")
//                        )
//                    )
//                )
//            createOrderActions.create(order)
//        })

//        paymentButtonContainer?.setup(
//            createOrder =
//            CreateOrder { createOrderActions ->
//                val order =
//                    OrderRequest(
//                        intent = OrderIntent.CAPTURE,
//                        appContext = AppContext(userAction = UserAction.PAY_NOW),
//                        purchaseUnitList =
//                        listOf(
//                            PurchaseUnit(
//                                amount =
//                                Amount(currencyCode = CurrencyCode.USD, value = "10.00")
//                            )
//                        )
//                    )
//                createOrderActions.create(order)
//            },
//            onApprove =
//            OnApprove { approval ->
//                Log.i(TAG, "OrderId: ${approval.data.orderId}")
//            }
//        )
    }

    fun showStripeSheet(clientSecret: String) {
        PaymentConfiguration.init(
            requireActivity().applicationContext,
//            stripPublicKey
            "pk_test_51LMwtTIXOwead2Sp6mZEM5tGaiZT363HLHm58hq7Wrip8KOH2Jj1U303ONw2DMd6oTGHP0uLiDw197LA0jauVeMG00HtE9n9nM"
        )

        paymentSheet.presentWithPaymentIntent(
            clientSecret, PaymentSheet.Configuration(
                merchantDisplayName = "Raseef",
//                customer = customerConfig,
                // Set `allowsDelayedPaymentMethods` to true if your business
                // can handle payment methods that complete payment after a delay, like SEPA Debit and Sofort.
                allowsDelayedPaymentMethods = false
            )
        )
    }

    private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {


        when (paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                Log.d("PaymentSheetResult", "onPaymentSheetResult: Canceled")

            }

            is PaymentSheetResult.Failed -> {

                Log.d("PaymentSheetResult", "onPaymentSheetResult: Failed")

            }

            is PaymentSheetResult.Completed -> {
                // Display for example, an order confirmation screen

                Log.d("PaymentSheetResult", "onPaymentSheetResult: Completed")
                findNavController().navigate(R.id.action_checkOutFragment_to_orderPlacedFragment)
//                val params = JsonObject()
//
//                params.addProperty("shopId", shopId)
//                params.addProperty("payment_intent_id", payment_intent_id)
//
//                Log.d("PaymentSheetResult", "onPaymentSheetResult: Completed ${params}")
//
//                stripeVerifyPay(params)

            }
        }
    }


    private fun createOrderJsonObject(): JsonObject {
        val params = JsonObject()
        try {

            params.add(
                "coordinates", Gson().toJsonTree(Coordinates(24.90147393769095, 24.90147393769095))
            )
            params.add(
                "shippingAddress", Gson().toJsonTree(
                    ShippingAddress(
                        "voluptas",
                        "Appartment",
                        457,
                        "Branding",
                        "Bogisich Points",
                        135,
                        "3886 Cummerata Burg"
                    )
                )
            )

            if (addNote.isNotEmpty()) {
                params.addProperty("specialNote", addNote)
            }
            params.addProperty("useWallet", mViewDataBinding.swOnOff.isChecked)
//            params.addProperty("orderType", "CASH_ON_DELIVERY")
            params.addProperty("orderType", "ONLINE_PAYMENTS")
            params.addProperty("payBy", "STRIPE")

//            params.addProperty("lat", 24.90147393769095)
//            params.addProperty("lng", 7.11531056779101)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return params
    }

    override fun onMapReady(p0: GoogleMap) {
        Log.d("onMapReady", "onMapReady: if")
        val location =
            LatLng(24.902204583353058, 67.11535962960994) // Example location (San Francisco)

        p0.uiSettings.isZoomControlsEnabled = false
        p0.uiSettings.isScrollGesturesEnabled = false
        p0.uiSettings.isRotateGesturesEnabled = false
        p0.uiSettings.isTiltGesturesEnabled = false
        p0.uiSettings.isZoomGesturesEnabled = false
        p0.uiSettings.isMapToolbarEnabled = false

        p0.addMarker(
            MarkerOptions().position(location).title("Marker Title").snippet("Marker Description")
        )

        p0.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
    }


}