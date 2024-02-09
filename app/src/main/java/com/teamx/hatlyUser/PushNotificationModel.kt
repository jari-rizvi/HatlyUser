package com.teamx.hatlyUser

import androidx.annotation.Keep

@Keep
data class PushNotificationModel(
    val orderId: String,
    val status: String
)