package com.teamx.hatlyUser.data.models

data class Product(
    val afterCheckOut: Int,
    val id: String,
    val image: String,
    val inStock: Boolean,
    val prize: Int,
    val productId: String,
    val productName: String,
    val quantity: Int,
    val quantityInStock: Int
)