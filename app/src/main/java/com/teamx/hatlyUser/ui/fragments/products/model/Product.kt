package com.teamx.hatlyUser.ui.fragments.products.model
import androidx.annotation.Keep

@Keep
data class Product(
    val _id: String,
    val description: String,
    val images: List<String>,
    val maxPrize: Int,
    val minPrize: Int,
    val name: String,
    val optionalVeriatons: List<OptionalVeriaton>,
    val productType: String,
    val shopId: String,
    val shopVeriations: List<ShopVeriation>,
    val veriations: List<Veriation>
)