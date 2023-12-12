package com.teamx.hatlyUser.ui.fragments.profile.specialOrderHistory.model

import androidx.annotation.Keep

@Keep
data class ModelSpecialHistory(
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