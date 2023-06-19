package com.teamx.hatlyUser.data.remote

import com.google.gson.JsonObject
import com.teamx.hatlyUser.constants.NetworkCallPoints
import com.teamx.hatlyUser.ui.fragments.auth.forgotpassword.model.ModelForgot
import com.teamx.hatlyUser.ui.fragments.auth.login.Model.ModelLogin
import com.teamx.hatlyUser.ui.fragments.auth.otp.model.ModelVerifyOtp
import com.teamx.hatlyUser.ui.fragments.auth.signup.model.ModelSignUp
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {
    @POST(NetworkCallPoints.SIGN_UP)
    suspend fun signup(@Body params: JsonObject?): Response<ModelSignUp>

    @POST(NetworkCallPoints.VERIFY_OTP)
    suspend fun verify_otp(@Body params: JsonObject?): Response<ModelVerifyOtp>

    @POST(NetworkCallPoints.LOGIN)
    suspend fun login(@Body params: JsonObject?): Response<ModelLogin>

    @POST(NetworkCallPoints.FORGOT)
    suspend fun forgot(@Body params: JsonObject?): Response<ModelForgot>
}