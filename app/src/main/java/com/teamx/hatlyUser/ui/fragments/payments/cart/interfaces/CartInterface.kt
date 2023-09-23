package com.teamx.hatlyUser.ui.fragments.payments.cart.interfaces


interface CartInterface {
    fun updateQuantity(position: Int, quantity: Int)
    fun removeCartItem(position: Int)
}