package com.teamx.hatlyUser.ui.fragments.hatlymart.stores.model
import androidx.annotation.Keep

@Keep
data class Doc(
    val _id: String,
    val address: Address,
    val balance: Balance,
    val bankAccountDetails: BankAccountDetails,
    val delivery: Delivery,
    val distance: Int,
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