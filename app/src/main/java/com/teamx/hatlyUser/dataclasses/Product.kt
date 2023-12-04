package com.teamx.hatlyUser.dataclasses

import androidx.annotation.Keep

@Keep
data class Product(
    val order_quantity: Int,
    val product_id: String,
    val subtotal: Double,
    val unit_price: Double,
    val variation_option_id: String
)