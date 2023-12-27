package com.teamx.hatlyUser.ui.fragments.wallet.model.transaction
import androidx.annotation.Keep

@Keep
data class ReceiverLocation(
    val location: Location,
    val locationId: String,
    val phoneNumber: String
)