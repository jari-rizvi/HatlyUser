package com.teamx.hatlyUser.ui.fragments.hatlymart.stores.model

data class ModelAllStoresItem(
    val _id: String,
    val delivery: Deliverys,
    val image: Images,
    val name: String,
    val ratting: String,
    val shopAddress: ShopAddresses,
    val totalReviews: Int
)