package com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.model.categoryModel

import androidx.annotation.Keep

@Keep
data class ModelCategory(
    val docs: List<Doc>,
    val hasNextPage: Boolean,
    val hasPrevPage: Boolean,
    val limit: Int,
    val nextPage: Any,
    val page: Int,
    val pagingCounter: Int,
    val prevPage: Int,
    val totalDocs: Int,
    val totalPages: Int
)