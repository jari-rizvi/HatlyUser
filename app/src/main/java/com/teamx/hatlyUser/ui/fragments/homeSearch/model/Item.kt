package com.teamx.hatlyUser.ui.fragments.homeSearch.model
import androidx.annotation.Keep

@Keep
data class Item(
    val _id: Id,
    val createdAt: String,
    val description: String,
    val images: List<String>,
    val isEnabled: Boolean,
    val maxPrice: String,
    val menuCategory: MenuCategory,
    val minPrice: String,
    val name: String,
    val price: String,
    val productType: String,
    val quantity: Int,
    val salePrice: Int,
    val shopId: ShopId,
    val sold: Int,
    val updatedAt: String,
    val veriations: List<Veriation>
)