package com.teamx.hatlyUser.ui.fragments.hatlymart.stores.model

data class ModelAllStoresItem(
    var _id: String,
    var delivery: Delivery?,
    var image: Image?,
    var name: String,
    var ratting: String,
    var shopAddress: ShopAddress?,
    var totalReviews: Int
)