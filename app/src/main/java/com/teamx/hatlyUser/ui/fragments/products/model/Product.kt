package com.teamx.hatlyUser.ui.fragments.products.model
import androidx.annotation.Keep

@Keep
data class Product(
    val _id: String,
    val description: String,
    val frequentlyBought: List<Any>,
    val images: List<String>,
    val quantity: Int,
    val prize: Double,
    val maxPrize: Double,
    val minPrize: Double,
    val name: String,
    val productType: String,
    val shopId: String,
    val veriations: List<Veriation>
)