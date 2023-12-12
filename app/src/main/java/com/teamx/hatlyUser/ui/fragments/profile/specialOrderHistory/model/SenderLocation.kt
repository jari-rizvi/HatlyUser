package com.teamx.hatlyUser.ui.fragments.profile.specialOrderHistory.model
import androidx.annotation.Keep

@Keep
data class SenderLocation(
    val location: Location,
    val phoneNumber: String
)