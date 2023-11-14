package com.teamx.hatlyUser.ui.fragments.hatlymart.stores.model
import androidx.annotation.Keep

@Keep
data class TradeLicence(
    val tradeLicenseExpireAt: String,
    val tradeLicenseIssueAt: String,
    val tradeLicenseUrl: String
)