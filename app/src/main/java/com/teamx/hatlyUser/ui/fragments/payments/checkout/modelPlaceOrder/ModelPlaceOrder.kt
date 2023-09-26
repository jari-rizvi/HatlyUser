package com.teamx.hatlyUser.ui.fragments.payments.checkout.modelPlaceOrder

data class ModelPlaceOrder(
    val __v: Int,
    val _id: String,
    val customer: String,
    val deliveryCharges: Int,
    val merchant: String,
    val orderType: String,
    val products: List<Product>,
    val shippingAddress: ShippingAddress,
    val shop: String,
    val specialNote: String,
    val status: String,
    val subTotal: Int,
    val tax: Int,
    val total: Int,
    val useWallet: Boolean,
    val usedWalletAmount: Int
)