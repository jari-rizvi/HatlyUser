package com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.modelShopHome
import androidx.annotation.Keep

@Keep
data class Shop(
    val __v: Int,
    val _id: String,
    val address: Address,
    val createdAt: String,
    val delivery: Delivery,
    val image: Image,
    val isEnabled: Boolean,
    val isOpen: Boolean?,
    val name: String,
    val owner: String,
    val products: List<String>,
    val rank: Int,
    val rattingCount: RattingCount?,
    val rattingSum: RattingSum?,
    val shopCategory: List<String>,
    val totalReviews: Int,
    val type: String,
    val updatedAt: String
)