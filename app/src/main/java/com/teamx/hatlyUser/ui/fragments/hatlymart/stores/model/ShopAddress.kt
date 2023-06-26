package com.teamx.hatlyUser.ui.fragments.hatlymart.stores.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class ShopAddress(
    @SerializedName("city")
    var city: String,
    @SerializedName("country")
    var country: String,
    @SerializedName("googleMapAddress")
    var googleMapAddress: String,
    @SerializedName("phoneCode")
    var phoneCode: String,
    @SerializedName("state")
    var state: String
)