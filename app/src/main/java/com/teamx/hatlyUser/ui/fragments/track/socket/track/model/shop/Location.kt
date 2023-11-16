package com.teamx.hatlyUser.ui.fragments.track.socket.track.model.shop
import androidx.annotation.Keep

@Keep
data class Location(
    val city: String,
    val coordinates: List<Double>,
    val country: String,
    val formattedAddress: String,
    val lat: Double,
    val lng: Double,
    val state: String,
    val type: String
)