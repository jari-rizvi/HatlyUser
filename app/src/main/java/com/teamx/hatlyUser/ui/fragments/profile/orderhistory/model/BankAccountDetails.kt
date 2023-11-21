package com.teamx.hatlyUser.ui.fragments.profile.orderhistory.model
import androidx.annotation.Keep

@Keep
data class BankAccountDetails(
    val accountHolderEmail: String,
    val accountHolderName: String,
    val accountNumber: String,
    val bankName: String
)