package com.teamx.hatlyUser.ui.fragments.auth.login.Model

import androidx.annotation.Keep

@Keep
data class ModelLogin(
    val _id: String,
    val contact: String,
    val email: String,
    val isEnabled: Boolean,
    val name: String,
    val password: String,
    val role: String,
    val token: String,
    val verificationToken: String,
    val deviceData: String,
    val verified: Boolean
)