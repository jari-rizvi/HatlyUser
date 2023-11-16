package com.teamx.hatlyUser.ui.fragments.track.socket.track.model.shop

import androidx.annotation.Keep

@Keep
data class TrackShopModel(
    val _id: String,
    val address: Address,
    val balance: Balance,
    val bankAccountDetails: BankAccountDetails,
    val createdAt: String,
    val delivery: Delivery,
    val image: String,
    val isEnabled: Boolean,
    val isOpen: Boolean,
    val name: String,
    val owner: String,
    val preparationTime: Int,
    val products: List<Any>,
    val rank: Int,
    val ratting: String,
    val resturantCategory: List<String>,
    val setting: Setting,
    val shopCategory: List<Any>,
    val status: String,
    val totalRating: Double,
    val totalReviews: Int,
    val tradeLicence: TradeLicence,
    val type: String,
    val updatedAt: String
)