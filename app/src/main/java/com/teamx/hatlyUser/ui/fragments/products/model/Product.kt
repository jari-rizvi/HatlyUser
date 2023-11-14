package com.teamx.hatlyUser.ui.fragments.products.model
import androidx.annotation.Keep

@Keep
data class Product(
    val _id: String,
    val description: String,
    val frequentlyBought: List<Any>,
    val images: List<String>,
    val quantity: Int,
    val price: Double?,
    val salePrice: Double?,
    val maxPrice: Double?,
    val minPrice: Double?,
    val name: String,
    val productType: String,
    val shopId: String,
    val veriations: List<Veriation>?
)