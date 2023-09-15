package com.teamx.hatlyUser.ui.fragments.foods.FoodsHome.models.modelCategory

import androidx.annotation.Keep

@Keep
data class Doc(
    val _id: String,
    val image: String,
    val title: String,
    var itemSelected : Boolean = false
)