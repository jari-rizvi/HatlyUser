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


        const val ALL_HEALTH_LIST = "health/list"
        const val HEALTH_DETAILS = "health/detail/{id}"

        const val ALL_FOODS_CATEGORIES = "shop/category/list"
        const val ALL_FOODS_SHOPS = "resturant/all"

        const val SHOP_FOODS = "food/{id}"
    }
}