package com.teamx.hatlyUser.ui.fragments.profile.specialOrderHistory.model
import androidx.annotation.Keep

@Keep
data class Doc(
    val _id: String,
    val details: Details,
    val fare: Double,
    val receiverLocation: ReceiverLocation,
    val senderId: String,
    val senderLocation: SenderLocation,
    val status: String,
    val trackingNumber: String
)