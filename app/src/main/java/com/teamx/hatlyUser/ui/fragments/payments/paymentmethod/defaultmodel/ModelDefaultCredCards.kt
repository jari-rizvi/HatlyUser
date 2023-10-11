package com.teamx.hatlyUser.ui.fragments.payments.paymentmethod.defaultmodel

data class ModelDefaultCredCards(
    val address: Any,
    val balance: Int,
    val created: Int,
    val currency: Any,
    val default_source: Any,
    val delinquent: Boolean,
    val description: Any,
    val discount: Any,
    val email: String,
    val id: String,
    val invoice_prefix: String,
    val invoice_settings: InvoiceSettings,
    val livemode: Boolean,
    val metadata: Metadata,
    val name: String,
    val next_invoice_sequence: Int,
    val `object`: String,
    val phone: String,
    val preferred_locales: List<Any>,
    val shipping: Any,
    val tax_exempt: String,
    val test_clock: Any
)