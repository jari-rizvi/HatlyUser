package com.teamx.hatlyUser.dataclasses

import androidx.annotation.Keep

@Keep
data class Balance(
    val _id: String,
    val admin_commission_rate: Int,
    val current_balance: Int,
    val payment_info: PaymentInfo,
    val total_earnings: Int,
    val withdrawn_amount: Int
)