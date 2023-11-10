package com.teamx.hatlyUser.ui.fragments.homeSearch.model
import androidx.annotation.Keep

@Keep
data class Item(
    val _id: String,
    val category: String,
    val description: String,
    val images: List<String>,
    val isEnabled: Boolean,
    val maxPrice: Int,
    val menuCategory: String,
    val minPrice: Int,
    val name: String,
    val price: Int?,
    val productType: String,
    val quantity: Int,
    val salePrice: Int,
    val shopId: String,
    val sold: Int,
    val subCategory: String,
    val veriations: List<Veriation>
)