package com.teamx.hatlyUser.ui.fragments.payments.checkout.model
import androidx.annotation.Keep

@Keep
data class Product(
    val afterCheckOut: Int,
    val id: String,
    val image: String,
    val inStock: Boolean,
    val prize: Double,
    val productId: String,
    val productName: String,
    val specialInstruction: String,
    val quantity: Int,
    val quantityInStock: Int
)