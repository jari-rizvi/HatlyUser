package com.teamx.hatlyUser.ui.fragments.auth.otp.model

import androidx.annotation.Keep

@Keep
data class ModelVerifyPassOtp(
    val uniqueId: String,
    val verified: Boolean
)