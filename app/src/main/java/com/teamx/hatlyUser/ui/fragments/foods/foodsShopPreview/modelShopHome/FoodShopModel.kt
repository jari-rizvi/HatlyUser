package com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.modelShopHome
import androidx.annotation.Keep

@Keep
data class FoodShopModel(
    val products: List<Product>,
    val shop: Shop
)