package com.teamx.hatlyUser.ui.fragments.shophome.model
import androidx.annotation.Keep

@Keep
data class Document(
    val _id: String,
    val description: String,
    val images: List<String>,
    val name: String,
    val price: Double,
    val salePrice: Double,
    val productType: String,
    val quantity: Int,
    val shopId: String,
    var cartExistence: Boolean,
    var cartQuantity: Int,
    var cartItemId: String,
)