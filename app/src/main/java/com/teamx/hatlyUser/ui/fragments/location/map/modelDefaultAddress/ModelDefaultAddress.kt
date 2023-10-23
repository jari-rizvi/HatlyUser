package com.teamx.hatlyUser.ui.fragments.location.map.modelDefaultAddress

import androidx.annotation.Keep
import com.teamx.hatlyUser.ui.fragments.auth.login.Model.Location

@Keep
data class ModelDefaultAddress(
    val message: String,
    val success: Boolean,
    val data: Location?
)