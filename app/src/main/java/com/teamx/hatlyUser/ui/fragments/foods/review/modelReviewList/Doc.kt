package com.teamx.hatlyUser.ui.fragments.foods.review.modelReviewList
import androidx.annotation.Keep

@Keep
data class Doc(
    val _id: String,
    var createdAt: String,
    val description: String,
    val images: List<String>,
    val name: String,
    val ratting: String,
    val shopId: String,
    val userId: String,
    val profileImage: String
)