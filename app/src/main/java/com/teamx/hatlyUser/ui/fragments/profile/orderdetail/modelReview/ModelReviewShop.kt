package com.teamx.hatlyUser.ui.fragments.profile.orderdetail.modelReview

import androidx.annotation.Keep

@Keep
data class ModelReviewShop(
    val _id: String,
    val description: String,
    val images: List<String>,
    val name: String,
    val ratting: String,
    val shopId: String,
    val userId: String
)