package com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.model
import androidx.annotation.Keep

@Keep
data class PopularProduct(
    val _id: String,
    val images: List<Image>,
    val name: String,
    val prize: String
)