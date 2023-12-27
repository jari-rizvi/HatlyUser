package com.teamx.hatlyUser.ui.fragments.wallet.model.transaction
import androidx.annotation.Keep

@Keep
data class WalletMetaData(
    val afterAmount: Double,
    val beforeAmount: Double
)