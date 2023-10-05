package com.teamx.hatlyUser.ui.fragments.payments.checkout.model
import androidx.annotation.Keep

@Keep
data class Option(
    val _id: String,
    val name: String,
    val prize: Int
)