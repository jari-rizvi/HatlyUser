package com.teamx.hatlyUser.ui.fragments.foods.review.modelReviewList

import androidx.annotation.Keep

@Keep
data class ModelReviewList(
    val docs: List<Doc>,
    val hasNextPage: Boolean,
    val hasPrevPage: Boolean,
    val limit: Int,
    val nextPage: Int,
    val page: Int,
    val pagingCounter: Int,
    val prevPage: Int,
    val totalDocs: Int,
    val totalPages: Int
)