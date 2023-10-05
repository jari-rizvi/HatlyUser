package com.teamx.hatlyUser.ui.fragments.products.model

import androidx.annotation.Keep

@Keep
data class ModelProductPreview(
    val product: Product,
    val recommended: List<Recommended>
)