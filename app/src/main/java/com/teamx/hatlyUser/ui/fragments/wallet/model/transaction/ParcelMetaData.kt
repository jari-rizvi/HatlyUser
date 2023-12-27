package com.teamx.hatlyUser.ui.fragments.wallet.model.transaction
import androidx.annotation.Keep

@Keep
data class ParcelMetaData(
    val _id: String,
    val createdAt: String,
    val details: Details,
    val driver: String,
    val fare: Double,
    val receiverLocation: ReceiverLocation,
    val senderId: String,
    val senderLocation: SenderLocation,
    val status: String,
    val trackingNumber: String
)