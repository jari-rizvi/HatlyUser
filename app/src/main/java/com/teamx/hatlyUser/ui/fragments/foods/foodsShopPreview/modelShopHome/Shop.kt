package com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.modelShopHome
import androidx.annotation.Keep

@Keep
data class Shop(
    val _id: String,
    val address: Address,
    val delivery: Delivery,
    val image: String,
    val isOpen: Boolean,
    val name: String,
    val owner: String,
    val ratting: Float,
    val totalReviews: Int,
    val type: String
)