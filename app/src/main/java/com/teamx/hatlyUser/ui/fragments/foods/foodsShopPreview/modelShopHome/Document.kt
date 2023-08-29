package com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.modelShopHome
import androidx.annotation.Keep

@Keep
data class Document(
    val _id: String,
    val category: String,
    val createdAt: String,
    val description: String,
    val images: Images?,
    val isEnabled: Boolean,
    val maxPrize: MaxPrize?,
    val minPrize: MinPrize?,
    val name: String,
    val optionalVeriatons: List<OptionalVeriaton>,
    val prize: Prize?,
    val productType: String,
    val quantity: Int,
    val relatedProducts: List<Any>,
    val shopCategory: String,
    val shopId: String,
    val sold: Int,
    val subCategory: String,
    val updatedAt: String,
    val veriations: List<Veriation>
)