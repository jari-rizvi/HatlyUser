package com.teamx.hatlyUser.ui.fragments.payments.checkout

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
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
import com.paypal.checkout.approve.Approval
import com.paypal.checkout.approve.OnApprove
import com.paypal.checkout.cancel.OnCancel
import com.paypal.checkout.config.CheckoutConfig
import com.paypal.checkout.config.Environment
import com.paypal.checkout.config.SettingsConfig
import com.paypal.checkout.createorder.CreateOrder
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.OrderIntent
import com.paypal.checkout.createorder.UserAction
import com.paypal.checkout.error.ErrorInfo
import com.paypal.checkout.error.OnError
import com.paypal.checkout.order.Amount
import com.paypal.checkout.order.AppContext
import com.paypal.checkout.order.OrderRequest
import com.paypal.checkout.order.PurchaseUnit
import com.paypal.checkout.shipping.OnShippingChange
import com.paypal.checkout.shipping.ShippingChangeActions
import com.paypal.checkout.shipping.ShippingChangeData
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentCheckOutBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.stores.model.Coordinates
import com.teamx.hatlyUser.ui.fragments.payments.checkout.adapter.CheckOutAdapter
import com.teamx.hatlyUser.ui.fragments.payments.checkout.model.Product
import com.teamx.hatlyUser.ui.fragments.payments.checkout.modelPlaceOrder.ShippingAddress
import com.teamx.hatlyUser.utils.PrefHelper
import com.teamx.hatlyUser.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException


@AndroidEntryPoint
class CheckOutFragment : BaseFragment<FragmentCheckOutBinding, CheckOutViewModel>(),
    OnMapReadyCallback {

    override val layoutId: Int
        get() = com.teamx.hatlyUser.R.layout.fragment_check_out
    override val viewModel: Class<CheckOutViewModel>
        get() = CheckOutViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var cartProductArrayList: ArrayList<Product>

    private lateinit var hatlyPopularAdapter: CheckOutAdapter

    private var addNote = ""

    private var mapFragment: SupportMapFragment? = null

    private lateinit var paymentSheet: PaymentSheet

    private var selectedPaymentMethod = PaymentMethod.CASH_ON_DELIVERY

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        options = navOptions {
            anim {
                enter = com.teamx.hatlyUser.R.anim.enter_from_left
                exit = com.teamx.hatlyUser.R.anim.exit_to_left
                popEnter = com.teamx.hatlyUser.R.anim.nav_default_pop_enter_anim
                popExit = com.teamx.hatlyUser.R.anim.nav_default_pop_exit_anim
            }
        }

        val userData = PrefHelper.getInstance(requireActivity()).getUserData()

        sharedViewModel.setlocationmodel(userData?.location)

        if (isAdded) {
            paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
        }

        mapFragment =
            childFragmentManager.findFragmentById(com.teamx.hatlyUser.R.id.mapFragment) as SupportMapFragment?
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
            findNavController().navigate(com.teamx.hatlyUser.R.id.action_checkOutFragment_to_paymentMethodFragment)
        }

        mViewDataBinding.txtLogin.setOnClickListener {
//            showPaypal()
            mViewModel.placeOrder(createOrderJsonObject())
        }

        mViewDataBinding.radioCash.setOnClickListener {
            if (mViewDataBinding.radioCash.isChecked) {
                mViewDataBinding.radioGroupStripe.visibility = View.GONE
                Log.d("onRadioButtonClicked", "onRadioButtonClicked: radioCash")
                selectedPaymentMethod = PaymentMethod.CASH_ON_DELIVERY
            }
        }

        mViewDataBinding.radioPayPal.setOnClickListener {
            if (mViewDataBinding.radioPayPal.isChecked) {
                mViewDataBinding.radioGroupStripe.visibility = View.GONE
                Log.d("onRadioButtonClicked", "onRadioButtonClicked: radioPayPal")
                selectedPaymentMethod = PaymentMethod.PAYPAL
            }
        }

        mViewDataBinding.radioOnline.setOnClickListener {
            if (mViewDataBinding.radioOnline.isChecked) {
                Log.d("onRadioButtonClicked", "onRadioButtonClicked: radioOnline")
                mViewDataBinding.radioGroupStripe.visibility = View.VISIBLE
                selectedPaymentMethod = PaymentMethod.ONLINE_PAYMENT
            }
        }

        mViewDataBinding.radioSelectedCard.setOnClickListener {
            mViewDataBinding.radio1.isChecked = true
            mViewDataBinding.radioAddNewCard.isChecked = false
        }

        mViewDataBinding.radioAddNewCard.setOnClickListener {
            mViewDataBinding.radio1.isChecked = false
            mViewDataBinding.radioAddNewCard.isChecked = true
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
            params.addProperty("lat", 24.901417466891772)
            params.addProperty("lng", 67.11549957694464)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        mViewDataBinding.swOnOff.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                val params = JsonObject()
                try {
                    params.add(
                        "coordinates",
                        Gson().toJsonTree(Coordinates(24.901417466891772, 67.11549957694464))
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
                        when (selectedPaymentMethod) {
                            PaymentMethod.CASH_ON_DELIVERY -> {
                                // Process payment for Cash on Delivery
                                if (data.status == "placed") {
                                    Log.d(
                                        "placeOrderResponse",
                                        "onViewCreated: CASH_ON_DELIVERY"
                                    )
                                    if (isAdded) {
                                        findNavController().navigate(com.teamx.hatlyUser.R.id.action_checkOutFragment_to_orderPlacedFragment)
                                    }
                                }
                            }
                            PaymentMethod.ONLINE_PAYMENT -> {
                                // Process payment for Online Payment
                                if (data.clientSecret != null) {
                                    Log.d(
                                        "placeOrderResponse",
                                        "onViewCreated: ONLINE_PAYMENT"
                                    )
                                    showStripeSheet(data.clientSecret)
                                }
                            }
                            PaymentMethod.PAYPAL -> {
                                Log.d(
                                    "placeOrderResponse",
                                    "onViewCreated: PAYPAL"
                                )
                                showPaypal()
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

        mViewModel.credCards()
        mViewModel.credCardsResponse.observe(requireActivity()) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }

                Resource.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    it.data?.let { data ->
                        mViewDataBinding.textView12155453.text = try {
                            "**** **** **** ${data.default!!.card.last4} | ${data.default.card.exp_month}/${data.default!!.card.exp_year}"
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

//        val config = CoreConfig("<CLIENT_ID>", environment = Environment.SANDBOX)
//
//        val payPalNativeClient = PayPalNativeCheckoutClient(
//            application = requireActvitiy().application,
//            coreConfig = coreConfig,
//            returnUrl = "<RETURN_URL>"
//        )


//        val YOUR_CLIENT_ID = "AZX5jqRs5Xi5XZacM1LBdmAqSzCRWslUa7Ic-vPu2bvHnzbePURxcYBSTl60fd6b5ga8djAajpRSYfVs"
////        val YOUR_CLIENT_ID = "AaTjrhT6DHDR5rRJLipZxrsxexzrMN9R8HP4VxloYCclYAruKo8lq6gHKit1F0z3y1MbHWqSdgApdwRk"
//        val config = CheckoutConfig(
//            application = requireActivity().application,
//            clientId = YOUR_CLIENT_ID,
//            environment = Environment.SANDBOX,
//            returnUrl = "nativexo://paypalpay",
////            currencyCode = CurrencyCode.USD,
//            userAction = UserAction.PAY_NOW,
//            settingsConfig = SettingsConfig(
//                loggingEnabled = true,
//                showWebCheckout = true
//            )
//        )
//        PayPalCheckout.setConfig(config)
//        PayPalCheckout.registerCallbacks(this, this, this, this)
    }

    //    var paymentButtonContainer: PaymentButtonContainer? = null
    private fun showPaypal() {
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
//            createOrderActions.create(order) { res ->
//                Log.d("createOrderActions", "OrderId: approve ${res}")
//            }
//        })

//        PayPalCheckout.start(CreateOrder { createOrderActions ->
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
//        }, onApprove = OnApprove { approval ->
//            Log.d("createOrderActions", "OrderId: approve ${approval.data.orderId}")
//        }, onCancel = OnCancel.invoke {
//            Log.d("createOrderActions", "OrderId: cancel")
//        })

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

    private fun showStripeSheet(clientSecret: String) {
        PaymentConfiguration.init(
            requireActivity().applicationContext,
//            stripPublicKey
            "pk_test_51LMwtTIXOwead2Sp6mZEM5tGaiZT363HLHm58hq7Wrip8KOH2Jj1U303ONw2DMd6oTGHP0uLiDw197LA0jauVeMG00HtE9n9nM"
        )

        paymentSheet.presentWithPaymentIntent(
            clientSecret, PaymentSheet.Configuration(
                merchantDisplayName = "Hatly",
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
                Log.d("placeOrderResponse", "onPaymentSheetResult: Canceled")

            }

            is PaymentSheetResult.Failed -> {

                Log.d("placeOrderResponse", "onPaymentSheetResult: Failed")

            }

            is PaymentSheetResult.Completed -> {
                // Display for example, an order confirmation screen

                Log.d("placeOrderResponse", "onPaymentSheetResult: Completed")
                findNavController().navigate(com.teamx.hatlyUser.R.id.action_checkOutFragment_to_orderPlacedFragment)
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
                "coordinates", Gson().toJsonTree(Coordinates(location.latitude, location.longitude))
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
//
            when (selectedPaymentMethod) {
                PaymentMethod.CASH_ON_DELIVERY -> {
                    // Process payment for Cash on Delivery
                    params.addProperty("orderType", "CASH_ON_DELIVERY")
                }

                PaymentMethod.ONLINE_PAYMENT -> {
                    // Process payment for Online Payment
                    params.addProperty("orderType", "ONLINE_PAYMENTS")
                    params.addProperty("payBy", "STRIPE")
                }

                PaymentMethod.PAYPAL -> {
                    params.addProperty("orderType", "ONLINE_PAYMENTS")
                    params.addProperty("payBy", "PAYPAL")
                }
            }


//            params.addProperty("lat", 24.90147393769095)
//            params.addProperty("lng", 7.11531056779101)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return params
    }

    lateinit var location : LatLng

    override fun onMapReady(p0: GoogleMap) {
        Log.d("onMapReady", "onMapReady: if")

        sharedViewModel.locationmodel.observe(requireActivity()) { locationModel ->

            if (locationModel != null) {
                location = LatLng(locationModel.lat, locationModel.lng) // Example location (San Francisco)
            } else {
                if (isAdded){
                    mViewDataBinding.root.snackbar("Add your location")
                }
            }
        }


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


//    override fun onApprove(approval: Approval) {
//        Log.d("createOrderActions", "OrderId: approve ${approval.data.orderId}")
//    }
//
//    override fun onCancel() {
//        Log.d("createOrderActions", "OrderId: onCancel ")
//    }
//
//    override fun onError(errorInfo: ErrorInfo) {
//        Log.d("createOrderActions", "OrderId: onError")
//    }
//
//    override fun onShippingChanged(
//        shippingChangeData: ShippingChangeData,
//        shippingChangeActions: ShippingChangeActions
//    ) {
//
//        Log.d("createOrderActions", "OrderId: onShippingChanged")
//    }


}


enum class PaymentMethod {
    CASH_ON_DELIVERY,
    ONLINE_PAYMENT,
    PAYPAL
}