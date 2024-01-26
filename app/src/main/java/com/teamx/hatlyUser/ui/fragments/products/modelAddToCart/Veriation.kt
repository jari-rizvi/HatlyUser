package com.teamx.hatlyUser.ui.fragments.products.modelAddToCart
import androidx.annotation.Keep

@Keep
data class Veriation(
    var _id: String,
    val price: Double?,
    var options: ArrayList<String>
)