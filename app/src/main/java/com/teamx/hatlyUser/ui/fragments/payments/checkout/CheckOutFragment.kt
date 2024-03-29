package com.teamx.hatlyUser.ui.fragments.payments.checkout

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
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
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.teamx.hatlyUser.BR
import com.teamx.hatlyUser.MainApplication
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.baseclasses.BaseFragment
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.databinding.FragmentCheckOutBinding
import com.teamx.hatlyUser.ui.fragments.hatlymart.stores.model.Coordinates
import com.teamx.hatlyUser.ui.fragments.payments.checkout.PaymentMethod.*
import com.teamx.hatlyUser.ui.fragments.payments.checkout.adapter.CheckOutAdapter
import com.teamx.hatlyUser.ui.fragments.payments.checkout.model.Product
import com.teamx.hatlyUser.utils.PrefHelper
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

    private var mapFragment: SupportMapFragment? = null

    private lateinit var paymentSheet: PaymentSheet

    private var selectedPaymentMethod = CASH_ON_DELIVERY

    private var paymentMethodid = ""

    private var orderId = ""

//    lateinit var paymentButtonContainer : PaymentButtonContainer

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

        val fullText = getString(R.string.by_completing_this_order_i_agree_to_all_terms_conditions)

        // Create a SpannableString
        // Create a SpannableString
        val spannableString = SpannableString(fullText)

        // Create a ClickableSpan for the clickable part

        // Create a ClickableSpan for the clickable part
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Handle click action here
                // For example, you can open a new activity, show a dialog, etc.
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data =
//                Uri.parse("https://sites.google.com/view/zues-privacy-policy/home")
                    Uri.parse("https://sites.google.com/view/termsandconditionsforhatly/home")
                startActivity(openURL)
            }
        }

        // Set the ClickableSpan to the desired portion of the text

        // Set the ClickableSpan to the desired portion of the text
        spannableString.setSpan(
            clickableSpan,
            41,
            fullText.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // Set the text color for the clickable span

        // Set the text color for the clickable span
        spannableString.setSpan(
            ForegroundColorSpan(Color.RED),
            41, fullText.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )


        // Set the SpannableString to the TextView

        // Set the SpannableString to the TextView
        mViewDataBinding.txtCheckBox.text = spannableString

        // Make the TextView clickable

        // Make the TextView clickable
        mViewDataBinding.txtCheckBox.movementMethod = LinkMovementMethod.getInstance()


//        mViewDataBinding.textView2564.setOnClickListener {
//
//        }

//        paymentButtonContainer = view.findViewById(R.id.payment_button_container)

        val userData = PrefHelper.getInstance(requireActivity()).getUserData()

        sharedViewModel.setlocationmodel(userData?.location)

        if (isAdded) {
            paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
        }

        mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
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

//        showPaypal()

        mViewDataBinding.txtLogin.setOnClickListener {
//            showPaypal()
//            initializationPayPal2()
            if (mViewDataBinding.checkBox.isChecked) {
                when (selectedPaymentMethod) {
                    CASH_ON_DELIVERY -> {
                        mViewModel.placeOrder(createOrderJsonObject())
                    }
                    STRIPE_PAYMENT -> {
                        mViewModel.placeOrder(createOrderJsonObject())
                    }
                    STRIPE_SAVED_PAYMENT -> {
                        mViewModel.placeOrder(createOrderJsonObject())
                    }
                    PAYPAL -> {

                    }
                    STRIPE_NOT_SELECTED -> {
                        if (isAdded) {
                            mViewDataBinding.root.snackbar(getString(R.string.select_payment))
                        }
                    }
                }

            } else {
                if (isAdded) {

                    mViewDataBinding.root.snackbar(getString(R.string.please_read_and_accept_our_terms_and_conditions_before_placing_an_order))
                }
            }
        }

        mViewDataBinding.radioCash.setOnClickListener {
            if (mViewDataBinding.radioCash.isChecked) {
                mViewDataBinding.radio1.isChecked = false
                mViewDataBinding.radioAddNewCard.isChecked = false
                mViewDataBinding.radioGroupStripe.visibility = View.GONE
                selectedPaymentMethod = CASH_ON_DELIVERY
            }
        }

        mViewDataBinding.radioPayPal.setOnClickListener {
            if (mViewDataBinding.radioPayPal.isChecked) {
                mViewDataBinding.radio1.isChecked = false
                mViewDataBinding.radioAddNewCard.isChecked = false
                mViewDataBinding.radioGroupStripe.visibility = View.GONE
                selectedPaymentMethod = PAYPAL
            }
        }

        mViewDataBinding.radioOnline.setOnClickListener {
            if (mViewDataBinding.radioOnline.isChecked) {
//                mViewDataBinding.radio1.isChecked = true
                mViewDataBinding.radioGroupStripe.visibility = View.VISIBLE
                selectedPaymentMethod = STRIPE_NOT_SELECTED
            }
        }

        mViewDataBinding.radioSelectedCard.setOnClickListener {
            mViewDataBinding.radio1.isChecked = true
            mViewDataBinding.radioAddNewCard.isChecked = false
            selectedPaymentMethod = STRIPE_SAVED_PAYMENT
        }

        mViewDataBinding.radioAddNewCard.setOnClickListener {
            mViewDataBinding.radio1.isChecked = false
            mViewDataBinding.radioAddNewCard.isChecked = true
            selectedPaymentMethod = STRIPE_PAYMENT
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

        mViewDataBinding.swOnOff.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                val params = JsonObject()
                try {
//                    params.add(
//                        "coordinates",
//                        Gson().toJsonTree(
//                            Coordinates(
//                                userData!!.location.lat,
//                                userData.location.lng
//                            )
//                        )
//                    )
                    params.addProperty("useWallet", isChecked)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                mViewModel.orderSummary(params)
            } else {
                val params = JsonObject()
                try {
                    params.addProperty("lat", userData!!.location?.lat ?: 0.0)
                    params.addProperty("lng", userData.location?.lng ?: 0.0)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                mViewModel.checkout(params)
            }
        }

        //        24.90147393769095, 67.11531056779101
        val params = JsonObject()
        try {
            params.addProperty("lat", userData!!.location?.lat ?: 0.0)
            params.addProperty("lng", userData.location?.lng ?: 0.0)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        mViewModel.checkout(params)

        mViewModel.checkoutResponse.observe(requireActivity()) {
            when (it.status) {
                Resource.Status.AUTH -> {
                    loadingDialog.dismiss()
                    onToSignUpPage()
                }

                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }

                Resource.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    it.data?.let { data ->

                        Log.d("checkoutResponse", "onViewCreated: ${data}")

                        mViewDataBinding.textView21343.text = try {
                            "${data.balance} ${
                                MainApplication.context.getString(
                                    R.string.aed
                                )
                            }"
                        } catch (e: Exception) {
                            "0.0 ${
                                MainApplication.context.getString(
                                    R.string.aed
                                )
                            }"
                        }
                        mViewDataBinding.textView2144633.text = try {
                            "${data.subTotal} ${
                                MainApplication.context.getString(
                                    R.string.aed
                                )
                            }"
                        } catch (e: Exception) {
                            "0.0 ${
                                MainApplication.context.getString(
                                    R.string.aed
                                )
                            }"
                        }

                        mViewDataBinding.textView214467433.text = try {
                            "${data.deliveryCharges} ${
                                MainApplication.context.getString(
                                    R.string.aed
                                )
                            }"
                        } catch (e: Exception) {
                            "0.0 ${
                                MainApplication.context.getString(
                                    R.string.aed
                                )
                            }"
                        }

                        mViewDataBinding.textView2144678433.text = try {
                            "${data.total} ${
                                MainApplication.context.getString(
                                    R.string.aed
                                )
                            }"
                        } catch (e: Exception) {
                            "0.0 ${
                                MainApplication.context.getString(
                                    R.string.aed
                                )
                            }"
                        }


                        cartProductArrayList.clear()
                        data.products?.let { it1 -> cartProductArrayList.addAll(it1) }
                        hatlyPopularAdapter.notifyDataSetChanged()
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

        mViewModel.orderSummaryResponse.observe(requireActivity()) {
            when (it.status) {
                Resource.Status.AUTH -> {
                    loadingDialog.dismiss()
                    onToSignUpPage()
                }

                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }

                Resource.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    it.data?.let { data ->

                        Log.d("checkoutResponse", "onViewCreated: ${data}")

                        mViewDataBinding.textView21343.text = try {
                            "${data.balance} ${
                                MainApplication.context.getString(
                                    R.string.aed
                                )
                            }"
                        } catch (e: Exception) {
                            "0.0 ${
                                MainApplication.context.getString(
                                    R.string.aed
                                )
                            }"
                        }
                        mViewDataBinding.textView2144633.text = try {
                            "${data.subTotal} ${
                                MainApplication.context.getString(
                                    R.string.aed
                                )
                            }"
                        } catch (e: Exception) {
                            "0.0 ${
                                MainApplication.context.getString(
                                    R.string.aed
                                )
                            }"
                        }

                        mViewDataBinding.textView214467433.text = try {
                            "${data.deliveryCharges} ${
                                MainApplication.context.getString(
                                    R.string.aed
                                )
                            }"
                        } catch (e: Exception) {
                            "0.0 ${
                                MainApplication.context.getString(
                                    R.string.aed
                                )
                            }"
                        }

                        mViewDataBinding.textView2144678433.text = try {
                            "${data.total} ${
                                MainApplication.context.getString(
                                    R.string.aed
                                )
                            }"
                        } catch (e: Exception) {
                            "0.0 ${
                                MainApplication.context.getString(
                                    R.string.aed
                                )
                            }"
                        }


                        cartProductArrayList.clear()
                        data.products?.let { it1 -> cartProductArrayList.addAll(it1) }
                        hatlyPopularAdapter.notifyDataSetChanged()
                    }
                }

                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    mViewDataBinding.swOnOff.isChecked = false
                    if (isAdded) {

                        mViewDataBinding.root.snackbar(it.message!!)
                    }
                }
            }
        }



        if (!mViewModel.placeOrderResponse.hasActiveObservers()){
            mViewModel.placeOrderResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.AUTH -> {
                        loadingDialog.dismiss()
                        onToSignUpPage()
                    }

                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            orderId = data._id
                            val bundle = Bundle()
                            bundle.putString("orderId", orderId)

                            if (data.isPayed) {
                                findNavController().navigate(
                                    R.id.action_checkOutFragment_to_orderPlacedFragment,
                                    bundle
                                )
                            } else {
                                when (selectedPaymentMethod) {
                                    CASH_ON_DELIVERY -> {
                                        // Process payment for Cash on Delivery
                                        if (data.status == "placed") {
                                            if (isAdded) {
                                                findNavController().navigate(
                                                    R.id.action_checkOutFragment_to_orderPlacedFragment,
                                                    bundle
                                                )
                                            }
                                        }
                                    }

                                    STRIPE_PAYMENT -> {
                                        // Process payment for Online Payment
                                        if (data.clientSecret != null) {
                                            showStripeSheet(data.clientSecret)
                                        }
                                    }

                                    STRIPE_SAVED_PAYMENT -> {
                                        // Process payment for Online Payment
                                        if (data.status == "placed") {
                                            if (isAdded) {
                                                findNavController().navigate(
                                                    R.id.action_checkOutFragment_to_orderPlacedFragment,
                                                    bundle
                                                )
                                            }
                                        }
                                    }

                                    PAYPAL -> {
                                        showPaypal()
                                    }

                                    STRIPE_NOT_SELECTED -> {
                                        if (isAdded) {
                                            mViewDataBinding.root.snackbar(getString(R.string.select_payment))
                                        }
                                    }
                                }
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


        mViewModel.credCards()
        mViewModel.credCardsResponse.observe(requireActivity()) {
            when (it.status) {
                Resource.Status.AUTH -> {
                    loadingDialog.dismiss()
                    onToSignUpPage()
                }

                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }

                Resource.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    it.data?.let { data ->
                        mViewDataBinding.textView12155453.text = try {
                            "**** **** **** ${data.default!!.card.last4} | ${data.default.card.exp_month}/${data.default!!.card.exp_year}"
                        } catch (e: Exception) {
                            ""
                        }

                        if (data.default?.id?.isNotEmpty() == true) {
                            mViewDataBinding.radioSelectedCard.visibility = View.VISIBLE
                            paymentMethodid = data.default.id
                        } else {
                            mViewDataBinding.radioSelectedCard.visibility = View.GONE
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

//    private fun initializationPayPal2() {
//
//        val coreConfig = CoreConfig(
//            "AaTjrhT6DHDR5rRJLipZxrsxexzrMN9R8HP4VxloYCclYAruKo8lq6gHKit1F0z3y1MbHWqSdgApdwRk",
//            environment = Environment.SANDBOX
//        )
//        val payPalNativeClient = PayPalNativeCheckoutClient(
//            application = requireActivity().application,
//            coreConfig = coreConfig,
//            returnUrl = "${BuildConfig.APPLICATION_ID}://paypalpay",
//        )
//
//        payPalNativeClient.listener = object : PayPalNativeCheckoutListener {
//            override fun onPayPalCheckoutCanceled() {
//                Log.d("initializationPayPal2", "onPayPalCheckoutCanceled: ")
//            }
//
//            override fun onPayPalCheckoutFailure(error: PayPalSDKError) {
//                Log.d("initializationPayPal2", "onPayPalCheckoutFailure: ")
//            }
//
//            override fun onPayPalCheckoutStart() {
//                // the PayPal paysheet is about to show up
//                Log.d("initializationPayPal2", "onPayPalCheckoutStart: ")
//            }
//
//            override fun onPayPalCheckoutSuccess(result: PayPalNativeCheckoutResult) {
//                Log.d("initializationPayPal2", "onPayPalCheckoutSuccess: ")
//            }
//        }
//
//    }

    //    var paymentButtonContainer: PaymentButtonContainer? = null
    val PAYPAL_CLIENT_ID = "YOUR-CLIENT-ID-HERE"
    val PAYPAL_SECRET = "ONLY-FOR-QUICKSTART-DO-NOT-INCLUDE-SECRET-IN-CLIENT-SIDE-APPLICATIONS"

    private fun showPaypal() {

//
//        val YOUR_CLIENT_ID =
//            "ASH9ytXn-OhQaOV0gyHsOERpEqfVl7oJ3Mo_48XtGDeq_YnWBp4SyK8h-68pGGJw4j8kGI7D86YRdoqx"
//
//
////        val coreConfig = CoreConfig(YOUR_CLIENT_ID, environment = Environment.SANDBOX)
//
//        val payPalNativeClient = PayPalNativeCheckoutClient(
//            application = requireActivity().application,
//            coreConfig = coreConfig,
//            returnUrl = "${requireActivity().packageName}://paypalpay"
//        )
//
//        payPalNativeClient.listener = object : PayPalNativeCheckoutListener {
//            override fun onPayPalCheckoutCanceled() {
//                Log.d("createOrderActions", "OnCancel")
//            }
//
//            override fun onPayPalCheckoutFailure(error: PayPalSDKError) {
//                Log.d("createOrderActions", "onPayPalCheckoutFailure")
//            }
//
//            override fun onPayPalCheckoutStart() {
//                // the PayPal paysheet is about to show up
//                Log.d("createOrderActions", "onPayPalCheckoutStart")
//            }
//
//            override fun onPayPalCheckoutSuccess(result: PayPalNativeCheckoutResult) {
//                Log.d("createOrderActions", "OrderId: approve ${result.orderId}")
//            }
//
////            override fun onPayPalSuccess(result: PayPalNativeCheckoutResult) {
////                // order was approved and is ready to be captured/authorized
////            }
////            override fun onPayPalFailure(error: PayPalSDKError) {
////                // handle the error
////            }
////            override fun onPayPalCanceled() {
////                // the user canceled the flow
////            }
//        }

//
//        PayPalCheckout.registerCallbacks(
//            onApprove = OnApprove { approval ->
//                // Optional callback for when an order is approved
//                Log.d("createOrderActions", "OrderId: approve ${approval.data.orderId}")
//            },
//            onCancel = OnCancel {
//                // Optional callback for when a buyer cancels the paysheet
//                Log.d("createOrderActions", "OnCancel")
//            },
//            onError = OnError { errorInfo ->
//                // Optional error callback
//                Log.d("createOrderActions", "OnError")
//            },
//            onShippingChange = OnShippingChange { shippingChangeData, shippingChangeActions ->
//                // Optional onShippingChange callback.
//                Log.d("createOrderActions", "OnShippingChange")
//            }
//        )


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

//        PayPalCheckout.startCheckout(CreateOrder { createOrderActions ->
//            val order = OrderRequest(
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

//        paymentButtonContainer.setup(
//            createOrder = CreateOrder { createOrderActions ->
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
//                Log.d("createOrderActions", "OrderId: approve ${approval.data.orderId}")
//            }
//        )

//        paymentButtonContainer.setPayPalButtonUi(
//            paypalButtonUi = PayPalButtonUi(
//                PayPalButtonColor.BLUE,
//                PayPalButtonLabel.CHECKOUT,
//                PaymentButtonAttributes(
//                    PaymentButtonShape.ROUNDED,
//                    PaymentButtonSize.LARGE,
//                    isEnabled =  true
//                )
//            )
//        )
//
//        PayPalCheckout.registerCallbacks(
//            onApprove = OnApprove { approval ->
//                // Optional callback for when an order is approved
//                Log.d("createOrderActions", "OrderId: approve ${approval.data.orderId}")
//            },
//            onCancel = OnCancel {
//                // Optional callback for when a buyer cancels the paysheet
//                Log.d("createOrderActions", "OnCancel")
//            },
//            onError = OnError { errorInfo ->
//                // Optional error callback
//                Log.d("createOrderActions", "OnError")
//            },
//            onShippingChange = OnShippingChange { shippingChangeData, shippingChangeActions ->
//                // Optional onShippingChange callback.
//                Log.d("createOrderActions", "OnShippingChange")
//            }
//        )

//        PayPalCheckout.startCheckout(
//            createOrder = CreateOrder { createOrderActions ->
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
//            }
//        )

//        mViewDataBinding.paymentButtonContainer.setup(
//            CreateOrder { createOrderActions ->
//                val order = OrderRequest(
//                    intent = OrderIntent.CAPTURE,
//                    appContext = AppContext(
//                        userAction = UserAction.PAY_NOW
//                    ),
//                    purchaseUnitList = listOf(
//                        PurchaseUnit(
//                            amount = Amount(
//                                currencyCode = CurrencyCode.USD,
//                                value = "10.00"
//                            )
//                        )
//                    )
//                )
//                createOrderActions.create(order)
//            }
//        )


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
//            getString(R.string.stripe_key)
            "pk_test_51NM8SbAESDqUcVTloKKwPpIdtlkmmm595qL1D8BZHt5hWrKp7GrEaBiRXG6jXZYgtMRR0yk7eD7RzTp0fwyahzDu00xf6h8wvu"
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
                val bundle = Bundle()
                bundle.putString("orderId", orderId)
                findNavController().navigate(
                    R.id.action_checkOutFragment_to_orderPlacedFragment,
                    bundle
                )
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

//            params.add(
//                "coordinates", Gson().toJsonTree(Coordinates(location.latitude, location.longitude))
//            )
//
//            params.add(
//                "shippingAddress", Gson().toJsonTree(
//                    ShippingAddress(
//                        "voluptas",
//                        "Appartment",
//                        457,
//                        "Branding",
//                        "Bogisich Points",
//                        135,
//                        "3886 Cummerata Burg"
//                    )
//                )
//            )

            if (addNote.isNotEmpty()) {
                params.addProperty("specialNote", addNote)
            }
            params.addProperty("useWallet", mViewDataBinding.swOnOff.isChecked)
//
            when (selectedPaymentMethod) {
                CASH_ON_DELIVERY -> {
                    // Process payment for Cash on Delivery
                    params.addProperty("orderType", "CASH_ON_DELIVERY")
                }

                STRIPE_PAYMENT -> {
                    // Process payment for Online Payment
                    params.addProperty("orderType", "ONLINE_PAYMENTS")
                    params.addProperty("payBy", "STRIPE")
                }

                STRIPE_SAVED_PAYMENT -> {
                    // Process payment for Online Payment
                    params.addProperty("orderType", "ONLINE_PAYMENTS")
                    params.addProperty("payBy", "STRIPE")
                    params.addProperty("payment_method", paymentMethodid)
                }

                PAYPAL -> {
                    params.addProperty("orderType", "ONLINE_PAYMENTS")
                    params.addProperty("payBy", "PAYPAL")
                }
                else -> {
                }
            }


//            params.addProperty("lat", 24.90147393769095)
//            params.addProperty("lng", 7.11531056779101)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return params
    }

    lateinit var location: LatLng

    override fun onMapReady(p0: GoogleMap) {
        Log.d("onMapReady", "onMapReady: if")

        sharedViewModel.locationmodel.observe(requireActivity()) { locationModel ->

            if (locationModel != null) {
                location =
                    LatLng(locationModel.lat, locationModel.lng) // Example location (San Francisco)
            } else {
                if (isAdded) {
                    mViewDataBinding.root.snackbar(getString(R.string.add_your_location))
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
    STRIPE_PAYMENT,
    STRIPE_SAVED_PAYMENT,
    PAYPAL,
    STRIPE_NOT_SELECTED,
}