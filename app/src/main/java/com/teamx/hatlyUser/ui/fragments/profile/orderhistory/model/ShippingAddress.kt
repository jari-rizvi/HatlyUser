package com.teamx.hatlyUser.ui.fragments.profile.orderhistory.model
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep

@Keep
data class ShippingAddress(
    val additionalDirection: String,
    val addressType: String,
    val apartmentNumber: Int,
    val area: String,
    val building: String,
    val floor: Int,
    val streat: String
)