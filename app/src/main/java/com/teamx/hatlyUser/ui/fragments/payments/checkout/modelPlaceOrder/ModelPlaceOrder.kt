package com.teamx.hatlyUser.ui.fragments.payments.checkout.modelPlaceOrder

data class ModelPlaceOrder(
    val __v: Int,
    val _id: String,
    val customer: String,
    val deliveryCharges: Double,
    val merchant: String,
    val orderType: String,
    val products: List<Product>,
    val shippingAddress: ShippingAddress,
    val shop: String,
    val specialNote: String,
    val status: String,
    val subTotal: Double,
    val tax: Double,
    val total: Double,
    val useWallet: Boolean,
    val usedWalletAmount: Double
)