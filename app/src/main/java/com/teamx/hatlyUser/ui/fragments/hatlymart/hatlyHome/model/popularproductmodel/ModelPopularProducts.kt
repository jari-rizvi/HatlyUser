package com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.model.popularproductmodel
import androidx.annotation.Keep

@Keep
data class ModelPopularProducts(
    val docs: List<Doc>,
    val hasNextPage: Boolean,
    val hasPrevPage: Boolean,
    val limit: Int,
    val nextPage: Int,
    val page: Int,
    val pagingCounter: Int,
    val prevPage: Any,
    val totalDocs: Int,
    val totalPages: Int
)