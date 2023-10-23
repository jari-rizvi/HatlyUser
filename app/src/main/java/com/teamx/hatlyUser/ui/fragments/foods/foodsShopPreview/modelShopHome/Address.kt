package com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.modelShopHome
import androidx.annotation.Keep

@Keep
data class Address(
    val city: String,
    val country: String,
    val state: String,
    val streetAddress: String,
    val zip: Int
)