package com.teamx.hatlyUser.ui.fragments.hatlymart.stores.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Delivery(
    @SerializedName("unit")
    var unit: String,
    @SerializedName("value")
    var value: Int
)