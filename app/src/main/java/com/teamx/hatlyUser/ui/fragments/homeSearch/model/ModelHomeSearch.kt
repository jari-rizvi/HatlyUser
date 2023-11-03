package com.teamx.hatlyUser.ui.fragments.homeSearch.model

import androidx.annotation.Keep

@Keep
data class ModelHomeSearch(
    val docs: List<Doc>,
    val hasNextPage: Boolean,
    val hasPrevPage: Boolean,
    val limit: Int,
    val nextPage: Any,
    val page: Int,
    val pagingCounter: Int,
    val prevPage: Any,
    val totalDocs: Int,
    val totalPages: Int
)