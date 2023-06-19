package com.teamx.hatlyUser.ui.fragments.auth.signup.model

data class ModelSignUp(
    val _id: String,
    val contact: String,
    val createdAt: String,
    val email: String,
    val isEnabled: Boolean,
    val name: String,
    val password: String,
    val role: String,
    val updatedAt: String,
    val verificationToken: String,
    val verified: Boolean
)