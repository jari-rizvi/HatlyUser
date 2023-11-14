package com.teamx.hatlyUser.ui.fragments.products.model
import androidx.annotation.Keep

@Keep
data class Recommended(
    val _id: String,
    val description: String,
    val frequentlyBought: List<Any>,
    val images: List<String>,
    val name: String,
    val price: Double?,
    val salePrice: Double?,
    val maxPrice: Double?,
    val minPrice: Double?,
    val productType: String,
    val quantity: Int,
    val shopId: String,
    val shopVeriations: List<Any>,
    val veriations: List<Any>
)