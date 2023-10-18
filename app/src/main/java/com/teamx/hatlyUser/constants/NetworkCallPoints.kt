package com.teamx.hatlyUser.constants

class NetworkCallPoints {
    companion object {

        const val LOGIN = "auth/login/app"
        const val LOGIN_WITH_GOOGLE = "auth/login/google/android"
        const val SIGN_UP = "auth/signup/app"
        const val VERIFY_SIGNUP_OTP = "auth/verify/signup/otp"
        const val FORGOT_PASS = "auth/forgot/password/app"
        const val VERIFY_FORGOT_PASS = "auth/verify/forgot/otp"
        const val UPDATE_PASS = "auth/password/update/app"
        const val RESEND_OTP = "auth/resend/otp"


        const val ALL_SHOPS = "shop/all"
//        const val SHOP_CATEGOIES = "custom/category/all"
        const val SHOP_CATEGOIES = "shop/category/all"
        const val POPULAR_PRODUCTS = "product/all"
        const val SHOP_SUB_CATEGOIES = "product/list"

//        const val ALL_FOODS_CATEGORIES = "shop/category/list"
        const val ALL_FOODS_CATEGORIES = "resturant/category/list"
        const val ALL_FOODS_SHOPS = "resturant/all"

        const val SHOP_FOODS = "food/{id}"

        const val PROD_PREVIEW = "product/{id}"

        const val ADD_TO_CART = "cart/add"

        const val GET_ALL_CARTS = "cart/get"

        const val REMOVE_CART_ITEM = "cart/remove/{id}"

        const val UPDATE_CART_ITEM = "cart/update/quanity"

        const val CHECKOUT = "order/checkout"

        const val ORDER_SUMMARY = "order/ordersummery"

        const val PLACE_ORDER = "order/create"

        const val ORDER_HISTORY = "order/all"

//        const val UPLOAD_REVIEW_IMGS = "upload/reviewImages"
        const val UPLOAD_REVIEW_IMGS = "upload/uploadMultiple"

        const val CREDS_CARDS = "stripe/list/paymentMethods"

        const val DEFAULT_CREDS_CARDS = "stripe/set/paymentMethod"

        const val DETACH_CREDS_CARDS = "stripe/detach/paymentMethod"

        const val CREATE_ADDRESS = "locations"

        const val SET_DEFAULT_ADDRESS = "locations/user/{id}"

        const val UPDATE_ADDRESS = "locations/{id}"
    }
}