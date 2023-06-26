package com.teamx.hatlyUser.ui.fragments.hatlymart.stores.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ModelAllStoresItem(
    @SerializedName("_id")
    var _id: String,
    @SerializedName("delivery")
    var delivery: Delivery?,
    @SerializedName("image")
    var image: Image?,
    @SerializedName("name")
    var name: String,
    @SerializedName("ratting")
    var ratting: String,
    @SerializedName("shopAddress")
    var shopAddress: ShopAddress?,
    @SerializedName("totalReviews")
    var totalReviews: Int
)