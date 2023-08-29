package com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.model
import androidx.annotation.Keep

@Keep
data class ModelHealthDetail(
    val categores: List<Categore>,
    val popularProduct: List<PopularProduct>
)