package com.teamx.hatlyUser.ui.fragments.home.settingModel

import androidx.annotation.Keep

@Keep
data class SettingAdmin(
    val VAT: Int,
    val __v: Int,
    val _id: String,
    val deliveryCharges: Int,
    val maxPayout: Int,
    val minPayout: Int,
    val minWallet: Int,
    val parcelMaxHeight: Int,
    val parcelMaxLength: Int,
    val parcelMaxQty: Int,
    val parcelMaxWeight: Int,
    val parcelMaxWidth: Int
)