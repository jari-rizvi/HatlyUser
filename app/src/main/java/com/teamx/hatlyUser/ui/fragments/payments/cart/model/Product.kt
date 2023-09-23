package com.teamx.hatlyUser.ui.fragments.payments.cart.model
import androidx.annotation.Keep

@Keep
data class Product(
    val afterCheckOut: Int,
    val id: String,
    val image: String?,
    val inStock: Boolean,
    val prize: Double,
    val productId: String,
    val productName: String,
    var quantity: Int,
    val quantityInStock: Int,
    val specialInstruction: String
)