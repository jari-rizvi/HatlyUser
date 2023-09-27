package com.teamx.hatlyUser.ui.fragments.profile.orderhistory.model
import androidx.annotation.Keep

@Keep
data class OrderHistoryModel(
    val docs: List<Doc>?,
    val hasNextPage: Boolean,
    val hasPrevPage: Boolean,
    val limit: Int,
    val nextPage: Int? = null,
    val page: Int,
    val pagingCounter: Int,
    val prevPage: Any,
    val totalDocs: Int,
    val totalPages: Int
)