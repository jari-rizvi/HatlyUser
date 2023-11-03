package com.teamx.hatlyUser.ui.fragments.special.specialorder.model
import androidx.annotation.Keep
@Keep
data class DeliveredParcel(
    val _id: String,
    val details: Details,
    val dropOff: DropOff,
    val fare: Int,
    val pickup: Pickup,
    val senderId: String,
    val status: String,
    val trackingNumber: String
)