package com.teamx.hatlyUser.ui.fragments.foods.FoodsHome.models.modelShops

import androidx.annotation.Keep

@Keep
data class ModelFoodShops(
    val docs: List<Doc>,
    val hasNextPage: Boolean,
    val hasPrevPage: Boolean,
    val limit: Int,
    val nextPage: Int,
    val offset: Int,
    val page: Int,
    val pagingCounter: Int,
    val prevPage: Int,
    val totalDocs: Int,
    val totalPages: Int
)