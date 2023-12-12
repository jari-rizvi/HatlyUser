package com.teamx.hatlyUser.ui.fragments.profile.specialOrderHistory.model
import androidx.annotation.Keep

@Keep
data class Location(
    val _id: String,
    val additionalDirection: String,
    val address: String,
    val building: String,
    val coordinates: Coordinates,
    val id: String,
    val isDefault: Boolean,
    val isDeleted: Boolean,
    val label: String,
    val lat: Double,
    val lng: Double,
    val type: String,
    val userId: String
)