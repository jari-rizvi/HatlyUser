package com.teamx.hatlyUser.ui.fragments.profile.orderhistory.model
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep

@Keep
data class Delivery(
    val unit: String,
    val value: Int
)