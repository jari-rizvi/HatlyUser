package com.teamx.hatlyUser.ui.fragments.profile.orderhistory.model
import androidx.annotation.Keep

@Keep
data class Option(
    val _id: String,
    val name: String,
    val price: Double,
    val salePrice: Double
)