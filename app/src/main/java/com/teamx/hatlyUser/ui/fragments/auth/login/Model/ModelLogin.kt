package com.teamx.hatlyUser.ui.fragments.auth.login.Model

import androidx.annotation.Keep

@Keep
data class ModelLogin(
    val _id: String,
    val contact: String,
    val email: String,
    val name: String,
    val token: String,
    val verified: Boolean
)