package com.teamx.hatlyUser.ui.fragments.wallet.model.transaction
import androidx.annotation.Keep

@Keep
data class Doc(
    val _id: String,
    val amount: Double,
    val change: String,
    var createdAt: String,
    val `for`: String,
    val id: String,
    val parcelMetaData: ParcelMetaData,
    val payBy: String,
    val status: String,
    val totalAmount: Double,
    val useWallet: Boolean,
    val userId: String,
    val walletAmount: Double,
    val walletMetaData: WalletMetaData,
    val walletType: String
)