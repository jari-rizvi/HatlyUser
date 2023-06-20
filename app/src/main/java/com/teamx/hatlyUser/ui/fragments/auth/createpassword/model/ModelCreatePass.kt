package com.teamx.hatlyUser.ui.fragments.auth.createpassword.model

data class ModelCreatePass(
    val _id: String,
    val contact: String,
    val email: String,
    val isEnabled: Boolean,
    val name: String,
    val password: String,
    val role: String,
    val token: String,
    val verificationToken: String,
    val verified: Boolean
)