package com.teamx.hatlyUser.ui.fragments.payments.cart.model

import androidx.annotation.Keep

@Keep
data class ModelCart(
    val products: List<Product>?,
    val subTotal: Double,
    val tax: Double,
    val total: Double
)