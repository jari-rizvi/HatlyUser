package com.teamx.hatlyUser.ui.fragments.products.model

import androidx.annotation.Keep

@Keep
data class OptionalVeriaton(
    val name: String,
    val prize: Double,
    var isSelected: Boolean = false
)