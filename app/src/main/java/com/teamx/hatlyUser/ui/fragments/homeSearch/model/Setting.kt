package com.teamx.hatlyUser.ui.fragments.homeSearch.model
import androidx.annotation.Keep

@Keep
data class Setting(
    val closesAt: String,
    val contact: String,
    val location: Location,
    val opensAt: String
)