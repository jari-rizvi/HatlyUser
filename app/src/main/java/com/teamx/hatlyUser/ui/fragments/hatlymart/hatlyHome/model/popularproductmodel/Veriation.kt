package com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.model.popularproductmodel
import androidx.annotation.Keep

@Keep
data class Veriation(
    val options: List<Option>,
    val price: Double,
    val quantity: Int,
    val title: String
)