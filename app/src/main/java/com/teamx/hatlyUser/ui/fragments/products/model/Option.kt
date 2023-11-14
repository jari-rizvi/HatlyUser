package com.teamx.hatlyUser.ui.fragments.products.model
import androidx.annotation.Keep

@Keep
data class Option(
    val _id: String,
    val name: String,
    val price: Double,
    val salePrice: Double,
    val isSelected: Boolean = false
)