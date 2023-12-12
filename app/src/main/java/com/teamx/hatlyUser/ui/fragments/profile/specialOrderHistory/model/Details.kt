package com.teamx.hatlyUser.ui.fragments.profile.specialOrderHistory.model
import androidx.annotation.Keep

@Keep
data class Details(
    val description: String,
    val height: Int,
    val heightUnit: String,
    val item: String,
    val length: Int,
    val lengthUnit: String,
    val qty: Int,
    val weight: Int,
    val weightUnit: String,
    val width: Int,
    val widthUnit: String
)