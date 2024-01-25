package com.teamx.hatlyUser.ui.fragments.track.socket.track.model.rider

import androidx.annotation.Keep

@Keep
data class TrackRiderModel(
    val __v: Int,
    val _id: String,
    val contact: String,
    val createdAt: String,
    val deviceData: String,
    val email: String,
    val fcmToken: String,
    val isEnabled: Boolean,
    val name: String,
    val notificationCount: Int,
    val password: String,
    val profileImage: String,
    val role: String,
    val updatedAt: String,
    val verified: Boolean,
    val wallet: String
)