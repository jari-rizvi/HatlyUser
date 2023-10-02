package com.teamx.hatlyUser.ui.fragments.products.model

data class FrequentlyBought(
    val _id: String,
    val description: String,
    val images: List<String>,
    val name: String,
    val prize: Int,
    val productType: String,
    val quantity: Int,
    val shopId: String,
    val shopVeriations: List<Any>,
    val veriations: List<Veriation>
)