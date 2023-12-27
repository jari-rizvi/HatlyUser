package com.teamx.hatlyUser.ui.fragments.wallet.model.transaction

import androidx.annotation.Keep

@Keep
data class TransactionData(
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