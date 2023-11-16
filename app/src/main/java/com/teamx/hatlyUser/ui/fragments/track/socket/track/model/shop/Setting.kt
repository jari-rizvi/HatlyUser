package com.teamx.hatlyUser.ui.fragments.track.socket.track.model.shop
import androidx.annotation.Keep

@Keep
data class Setting(
    val closesAt: String,
    val contact: String,
    val location: Location,
    val opensAt: String
)