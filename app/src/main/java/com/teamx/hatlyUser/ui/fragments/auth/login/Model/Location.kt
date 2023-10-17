package com.teamx.hatlyUser.ui.fragments.auth.login.Model

import androidx.annotation.Keep

@Keep
data class Location(
    val _id: Id,
    val additionalDirection: String,
    val address: String,
    val apartmentNumber: Int,
    val building: String,
    val coordinates: Coordinates,
    val createdAt: String,
    val id: String,
    val label: String,
    val lat: Double,
    val lng: Double,
    val type: String,
    val updatedAt: String
)