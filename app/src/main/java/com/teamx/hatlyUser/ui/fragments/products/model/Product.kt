package com.teamx.hatlyUser.ui.fragments.products.model

data class Product(
    val _id: String,
    val description: String,
    val images: List<String>,
    val maxPrize: Int,
    val minPrize: Int,
    val name: String,
    val productType: String,
    val shopId: String,
    val veriations: List<Veriation>?
)