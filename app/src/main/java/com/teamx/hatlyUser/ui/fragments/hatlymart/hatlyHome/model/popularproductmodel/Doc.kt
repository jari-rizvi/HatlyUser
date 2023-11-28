package com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.model.popularproductmodel
import androidx.annotation.Keep

@Keep
data class Doc(
    val _id: String,
    val description: String,
    val images: List<String>?,
    val maxPrice: String,
    val minPrice: String,
    val name: String,
    val optionalVeriatons: List<OptionalVeriaton>,
    val price: Double,
    val salePrice: Double,
    val productType: String,
    val quantity: Int,
    val shopId: String,
    val veriations: List<Veriation>,
    var cartItemId: String,
    var cartExistence: Boolean,
    var cartQuantity: Int
)