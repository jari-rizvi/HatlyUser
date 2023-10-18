package com.teamx.hatlyUser.ui.fragments.profile.orderhistory.model
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep

@Keep
data class Shop(
    val _id: String,
    val address: Address,
    val createdAt: String,
    val delivery: Delivery,
    val image: String,
    val isOpen: Boolean,
    val name: String,
    val owner: String,
    val ratting: Double,
    val totalReviews: Int,
    val type: String
)