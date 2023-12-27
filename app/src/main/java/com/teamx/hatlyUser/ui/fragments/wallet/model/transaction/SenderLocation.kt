package com.teamx.hatlyUser.ui.fragments.wallet.model.transaction
import androidx.annotation.Keep

@Keep
data class SenderLocation(
    val location: Location,
    val locationId: String,
    val phoneNumber: String
)