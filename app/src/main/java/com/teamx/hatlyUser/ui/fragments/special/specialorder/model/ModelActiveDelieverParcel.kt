package com.teamx.hatlyUser.ui.fragments.special.specialorder.model

import androidx.annotation.Keep
@Keep
data class ModelActiveDelieverParcel(
    val activeParcels: List<ActiveParcel>,
    val deliveredParcels: List<DeliveredParcel>
)