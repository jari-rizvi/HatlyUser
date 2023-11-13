package com.teamx.hatlyUser.ui.fragments.special.specialorder.model
import androidx.annotation.Keep
@Keep
data class Details(
    val description: String,
    val height: Double,
    val heightUnit: String,
    val item: String,
    val length: Double,
    val lengthUnit: String,
    val qty: Int,
    val weight: Double,
    val weightUnit: String,
    val width: Double,
    val widthUnit: String
)