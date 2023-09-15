package com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.model.popularproductmodel
import androidx.annotation.Keep

@Keep
data class Doc(
    val _id: String,
    val description: String,
    val images: List<String>?,
    val maxPrize: String,
    val minPrize: String,
    val name: String,
    val optionalVeriatons: List<OptionalVeriaton>,
    val prize: String,
    val productType: String,
    val quantity: Int,
    val shopId: String,
    val veriations: List<Veriation>
)