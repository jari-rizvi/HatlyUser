package com.teamx.hatlyUser.ui.fragments.products.model
import androidx.annotation.Keep

@Keep
data class Veriation(
    val _id: String,
    val isMultiple: Boolean,
    val isRequired: Boolean,
    val options: List<Option>,
    val title: String,
    var selectedIndex: Int,
)