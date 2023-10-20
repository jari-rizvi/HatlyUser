package com.teamx.hatlyUser.ui.fragments.wishlist.modelWishList
import androidx.annotation.Keep
@Keep
data class Doc(
    val __v: Int,
    val _id: String,
    val averageRating: Double,
    val itemId: String,
    val itemType: String,
    val shop: Shop,
    val userId: String
)