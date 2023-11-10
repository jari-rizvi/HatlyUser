package com.teamx.hatlyUser.ui.fragments.homeSearch.model
import androidx.annotation.Keep

@Keep
data class Address(
    val city: String,
    val coordinates: Coordinates,
    val country: String,
    val googleMapAddress: String,
    val phoneCode: String,
    val state: String,
    val streetAddress: String,
    val zip: Int
)