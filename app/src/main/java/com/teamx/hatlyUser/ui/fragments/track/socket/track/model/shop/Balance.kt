package com.teamx.hatlyUser.ui.fragments.track.socket.track.model.shop
import androidx.annotation.Keep

@Keep
data class Balance(
    val current_balance: Int,
    val total_earnings: Int,
    val withdrawn_amount: Int
)