package com.teamx.hatlyUser.ui.fragments.foods.FoodsHome.models.modelShops

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