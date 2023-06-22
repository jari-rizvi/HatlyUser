package com.teamx.hatlyUser.constants

class NetworkCallPoints {
    companion object {
        const val SIGN_UP = "auth/signup/app"
        const val VERIFY_OTP = "auth/verify/otp"
        const val LOGIN = "auth/login/app"
        const val FORGOT = "auth/forgot/password/app"
        const val CREATE_PASS = "auth/password/update/app"
        const val RESEND_OTP = "auth/resend/otp"
        const val ALL_STORES = "health/list"
    }
}