package com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.modelShopHome
import androidx.annotation.Keep

@Keep
data class Document(
    val _id: String,
    val description: String,
    val images: List<String>,
    val maxPrice: Double,
    val menuCategory: String,
    val minPrice: Double,
    val name: String,
    val price: Double,
    val productType: String,
    val quantity: Int,
    val salePrice: Double,
    val shopId: String,
    val veriations: List<Veriation>
)