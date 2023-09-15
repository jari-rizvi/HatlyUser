package com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.model.categoryModel
import androidx.annotation.Keep

@Keep
data class Doc(
    val __v: Int,
    val _id: String,
    val image: String,
    val name: String,
    val shop: String,
    val subCategories: List<String>?,
    val isShowMore: Boolean = false
)