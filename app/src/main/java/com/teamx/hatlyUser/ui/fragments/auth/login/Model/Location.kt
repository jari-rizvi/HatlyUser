package com.teamx.hatlyUser.ui.fragments.auth.login.Model

import androidx.annotation.Keep

@Keep
data class Location(
    val _id: String,
    val additionalDirection: String,
    val address: String,
    val apartmentNumber: Int,
    val building: String,
    val coordinates: Coordinates?,
    val createdAt: Double,
    val userId: String,
    val label: String,
    val lat: Double,
    val lng: Double,
    val type: String,
    var isAction: String,
    val updatedAt: String,
    var isDefault: Boolean,
)