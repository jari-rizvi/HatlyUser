package com.teamx.hatlyUser.ui.fragments.home.model

import androidx.annotation.Keep

@Keep
data class FcmModel(
    val message: String,
    val success: Boolean
)