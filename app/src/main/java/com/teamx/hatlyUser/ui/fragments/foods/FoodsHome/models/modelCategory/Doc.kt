package com.teamx.hatlyUser.ui.fragments.foods.FoodsHome.models.modelCategory

import androidx.annotation.Keep

@Keep
data class Doc(
    val _id: String,
    val createdAt: String,
    val image: Image,
    val title: String,
    val updatedAt: String
)