package com.teamx.hatlyUser.ui.fragments.products.model

data class Option(
    val _id: String,
    val name: String,
    val prize: Double,
    var isSelected : Boolean = false
)