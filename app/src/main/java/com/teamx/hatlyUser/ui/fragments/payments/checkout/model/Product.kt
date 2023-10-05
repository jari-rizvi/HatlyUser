package com.teamx.hatlyUser.ui.fragments.payments.checkout.model
import androidx.annotation.Keep

@Keep
data class Product(
    val _id: String,
    val image: String,
    val prize: String,
    val productId: String,
    val productName: String,
    val quantity: Int,
    val shopId: String,
    val specialInstruction: String,
    val veriations: List<Veriation>
)