package com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.modelShopHome
import androidx.annotation.Keep
@Keep
data class Product(
    val _id: String,
    val count: Int,
    val documents: List<Document>,
    var isSelected: Boolean
)