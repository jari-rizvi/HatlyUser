package com.teamx.hatlyUser.ui.fragments.homeSearch.model
import androidx.annotation.Keep

@Keep
data class Option(
    val _id: String,
    val name: String,
    val price: Int,
    val salePrice: Int
)