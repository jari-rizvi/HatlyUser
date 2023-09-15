package com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.modelShopHome
import androidx.annotation.Keep

@Keep
data class Document(
    val _id: String,
    val description: String,
    val images: List<String>,
    val name: String,
    val prize: String,
    val productType: String,
    val quantity: Int,
    val shopId: String
)