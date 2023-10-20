package com.teamx.hatlyUser.ui.fragments.wishlist.modelWishList
import androidx.annotation.Keep
@Keep
data class Balance(
    val current_balance: Double,
    val total_earnings: Double,
    val withdrawn_amount: Double
)