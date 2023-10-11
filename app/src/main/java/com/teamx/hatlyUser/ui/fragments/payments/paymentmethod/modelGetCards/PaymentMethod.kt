package com.teamx.hatlyUser.ui.fragments.payments.paymentmethod.modelGetCards

data class PaymentMethod(
    val billing_details: BillingDetails,
    val card: Card,
    val created: Int,
    val customer: String,
    val id: String,
    val livemode: Boolean,
    val metadata: Metadata,
    val `object`: String,
    val type: String
)