package com.teamx.hatlyUser.ui.fragments.products.modelAddToCart

import androidx.annotation.Keep

@Keep
data class AddToCart(
    val success: Boolean,
    var cartItemId: String
)