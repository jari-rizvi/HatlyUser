package com.teamx.hatlyUser.dataclasses

import androidx.annotation.Keep

@Keep
data class CheckoutDataMV(
    val amount: Int,
    val billing_address: BillingAddress,
    val customer_contact: String,
    val delivery_fee: Int,
    val payment_gateway: String,
    val products: List<Product>,
    val shipping_address: ShippingAddress
)
