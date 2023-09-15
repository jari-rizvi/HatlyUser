package com.teamx.hatlyUser.ui.fragments.hatlymart.stores.model
import androidx.annotation.Keep

@Keep
data class Doc(
    val _id: String,
    val address: Address,
    val delivery: Delivery,
    val image: String,
    val isOpen: Boolean,
    val name: String,
    val owner: String,
    val ratting: Int,
    val totalReviews: Int,
    val type: String
)