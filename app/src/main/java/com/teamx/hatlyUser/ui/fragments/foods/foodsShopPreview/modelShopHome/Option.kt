package com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.modelShopHome
import androidx.annotation.Keep

@Keep
data class Option(
    val _id: String,
    val name: String,
    val price: Double,
    val salePrice: Double
)