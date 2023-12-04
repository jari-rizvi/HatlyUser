package com.teamx.hatlyUser.ui.fragments.profile.orderhistory.model
import androidx.annotation.Keep

@Keep
data class Doc(
    val __v: Int,
    val _id: String,
    val clientSecret: String,
    var createdAt: String,
    val customer: String,
    val deliveryCharges: Double,
    val dropOff: DropOff,
    val isPayed: Boolean,
    val merchant: String,
    val orderId: String,
    val orderType: String,
    val products: List<Product>,
    val shop: Shop,
    val specialNote: String,
    var status: String,
    val subTotal: Double,
    val tax: Double,
    val total: Double,
    val useWallet: Boolean,
    val usedWalletAmount: Double,
    var isFromWallet: Boolean
)