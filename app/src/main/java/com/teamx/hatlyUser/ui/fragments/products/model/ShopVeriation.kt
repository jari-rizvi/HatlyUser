package com.teamx.hatlyUser.ui.fragments.products.model
import androidx.annotation.Keep

@Keep
data class ShopVeriation(
    val __v: Int,
    val _id: String,
    val name: String,
    val options: List<String>,
    val shopId: String
)