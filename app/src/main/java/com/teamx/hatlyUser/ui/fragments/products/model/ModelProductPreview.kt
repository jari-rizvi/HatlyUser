package com.teamx.hatlyUser.ui.fragments.products.model
import androidx.annotation.Keep

@Keep
data class ModelProductPreview(
    val frequentlyBought: List<FrequentlyBought>?,
    val product: Product
)