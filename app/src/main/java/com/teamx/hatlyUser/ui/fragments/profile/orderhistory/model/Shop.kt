package com.teamx.hatlyUser.ui.fragments.profile.orderhistory.model
import androidx.annotation.Keep

@Keep
data class Shop(
    val _id: String,
    val address: Address,
    val balance: Balance,
    val bankAccountDetails: BankAccountDetails,
    val createdAt: String,
    val delivery: Delivery,
    val image: String,
    val isOpen: Boolean,
    val name: String,
    val owner: String,
    val preparationTime: Int,
    val ratting: Double,
    val reasonToRejectShop: Any,
    val setting: Setting,
    val status: String,
    val totalReviews: Int,
    val tradeLicence: TradeLicence,
    val type: String
)