package com.teamx.hatlyUser.ui.fragments.special.specialorder.model
import androidx.annotation.Keep
@Keep
data class Pickup(
    val address: String,
    val coordinates: Coordinates,
    val type: String
)