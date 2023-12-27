package com.teamx.hatlyUser.ui.fragments.special.ParcelLocation.createParcel
import androidx.annotation.Keep

@Keep
data class ModelCreateParcel(
    val details: Details,
    val receiverLocation: ReceiverLocation,
    val senderLocation: SenderLocation
)