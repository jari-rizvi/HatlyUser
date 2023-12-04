package com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.modelShopHome
import androidx.annotation.Keep

@Keep
data class Shop(
    val _id: String,
    val address: Address,
    val balance: Balance,
    val bankAccountDetails: BankAccountDetails,
    val delivery: Delivery,
    val distance: Double,
    val hasAddedToWishlist: Boolean,
    val image: String,
    val isOpen: Boolean,
    val name: String,
    val owner: String,
    val ratting: Double,
    val setting: Setting,
    val status: String,
    val totalReviews: Int,
    val tradeLicence: TradeLicence,
    val type: String
)