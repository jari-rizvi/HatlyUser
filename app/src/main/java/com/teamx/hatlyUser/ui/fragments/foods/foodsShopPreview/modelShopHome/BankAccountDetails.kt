package com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.modelShopHome
import androidx.annotation.Keep

@Keep
data class BankAccountDetails(
    val accountHolderEmail: String,
    val accountHolderName: String,
    val accountNumber: String,
    val bankName: String
)