package com.teamx.hatlyUser.ui.fragments.profile.orderhistory.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep

@Keep
data class Address(
    val city: String,
    val coordinates: Coordinates,
    val country: String,
    val googleMapAddress: String,
    val phoneCode: String,
    val state: String
)