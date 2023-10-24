package com.teamx.hatlyUser.ui.fragments.auth.signup.model

import androidx.annotation.Keep

@Keep
data class ModelSignUp(
    val _id: String,
    val contact: String,
    val email: String,
    val role: String,
    val wallet: Double,
    val verified: Boolean,
    val name: String
)