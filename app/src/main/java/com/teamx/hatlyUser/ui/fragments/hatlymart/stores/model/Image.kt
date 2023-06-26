package com.teamx.hatlyUser.ui.fragments.hatlymart.stores.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Image(
    @SerializedName("public_id")
    var public_id: String,
    @SerializedName("secure_url")
    var secure_url: String
)