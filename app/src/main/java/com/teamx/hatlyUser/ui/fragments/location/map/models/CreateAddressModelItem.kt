package com.teamx.hatlyUser.ui.fragments.location.map.models

import com.google.errorprone.annotations.Keep

@Keep
data class CreateAddressModelItem(
    val _id: String,
    val additionalDirection: String,
    val address: String,
    val apartmentNumber: Int,
    val building: String,
    val label: String,
    val lat: Double,
    val lng: Double,
    val userId: String,
    var isAction: String,
    var isSelected: Boolean = false,
)