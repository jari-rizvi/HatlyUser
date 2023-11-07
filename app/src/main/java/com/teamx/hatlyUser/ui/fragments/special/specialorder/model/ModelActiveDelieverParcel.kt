package com.teamx.hatlyUser.ui.fragments.special.specialorder.model

import androidx.annotation.Keep
import com.teamx.hatlyUser.ui.fragments.profile.orderhistory.model.Doc

@Keep
data class ModelActiveDelieverParcel(
    val activeParcels: List<ActiveParcel>,
    val deliveredParcels: List<DeliveredParcel>,
    val docs: List<DeliveredParcel>?,
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