package com.teamx.hatlyUser.ui.fragments.profile.orderhistory.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep


@Keep
data class Doc(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val customer: String,
    val deliveryCharges: Double,
    val merchant: String,
    val orderType: String,
    val products: List<Product>,
    val shippingAddress: ShippingAddress,
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



