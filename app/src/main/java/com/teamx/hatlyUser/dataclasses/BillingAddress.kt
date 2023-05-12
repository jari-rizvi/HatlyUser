package com.teamx.hatlyUser.dataclasses

import androidx.annotation.Keep

@Keep
data class BillingAddress(
    val _id: String,
    val city: String,
    val country: String,
    val state: String,
    val street_address: String,
    val zip: String
)