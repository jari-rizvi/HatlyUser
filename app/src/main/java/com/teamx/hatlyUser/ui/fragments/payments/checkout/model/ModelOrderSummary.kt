package com.teamx.hatlyUser.ui.fragments.payments.checkout.model

import androidx.annotation.Keep

@Keep
data class ModelOrderSummary(
    val __v: Int,
    val _id: String,
    val balance: Double?,
    val deliveryCharges: Double,
    val products: List<Product>?,
    val subTotal: Double,
    val tax: Double,
    val total: Double,
    val usedWalletAmount: Double
)