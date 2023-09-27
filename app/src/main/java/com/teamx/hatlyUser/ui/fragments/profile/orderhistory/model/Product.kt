package com.teamx.hatlyUser.ui.fragments.profile.orderhistory.model
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep

@Keep
data class Product(
    val afterCheckOut: Int,
    val id: String,
    val image: String,
    val inStock: Boolean,
    val prize: Int,
    val productId: String,
    val productName: String,
    val quantity: Int,
    val quantityInStock: Int
)