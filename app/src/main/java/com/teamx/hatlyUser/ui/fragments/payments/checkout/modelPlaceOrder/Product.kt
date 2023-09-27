package com.teamx.hatlyUser.ui.fragments.payments.checkout.modelPlaceOrder

data class Product(
    val afterCheckOut: Int,
    val id: String,
    val image: String,
    val inStock: Boolean,
    val prize: Double,
    val productId: String,
    val productName: String,
    val quantity: Int,
    val quantityInStock: Int,
    val specialInstruction: String
)