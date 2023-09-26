package com.teamx.hatlyUser.ui.fragments.payments.checkout.modelPlaceOrder

data class ShippingAddress(
    val additionalDirection: String,
    val addressType: String,
    val apartmentNumber: Int,
    val area: String,
    val building: String,
    val floor: Int,
    val streat: String
)