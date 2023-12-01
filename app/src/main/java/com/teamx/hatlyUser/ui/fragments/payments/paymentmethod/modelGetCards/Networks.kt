package com.teamx.hatlyUser.ui.fragments.payments.paymentmethod.modelGetCards
import androidx.annotation.Keep

@Keep
data class Networks(
    val available: List<String>,
    val preferred: Any
)