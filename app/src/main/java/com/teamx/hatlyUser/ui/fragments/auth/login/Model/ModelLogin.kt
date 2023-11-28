package com.teamx.hatlyUser.ui.fragments.auth.login.Model

import androidx.annotation.Keep

@Keep
data class ModelLogin(
    val __v: Int,
    val _id: String,
    var contact: String?,
    val coordinates: Coordinates,
    val email: String,
    var location: Location,
    var name: String,
    var profileImage: String,
    val role: String,
    val token: String,
    val verified: Boolean,
    val wallet: Double
)