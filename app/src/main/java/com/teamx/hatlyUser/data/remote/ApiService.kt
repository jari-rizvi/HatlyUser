package com.teamx.hatlyUser.data.remote

import com.google.gson.JsonObject
import com.teamx.hatlyUser.constants.NetworkCallPoints
import com.teamx.hatlyUser.constants.NetworkCallPointsNest.Companion.DEVICE_TOKEN
import com.teamx.hatlyUser.constants.NetworkCallPointsNest.Companion.TOKENER
import com.teamx.hatlyUser.ui.fragments.auth.createpassword.model.ModelUpdatePass
import com.teamx.hatlyUser.ui.fragments.auth.forgotpassword.model.ModelForgotPass
import com.teamx.hatlyUser.ui.fragments.auth.login.Model.ModelLogin
import com.teamx.hatlyUser.ui.fragments.auth.otp.model.ModelSignUpOtpVerify
import com.teamx.hatlyUser.ui.fragments.auth.otp.model.ModelVerifyPassOtp
import com.teamx.hatlyUser.ui.fragments.auth.signup.model.ModelSignUp
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.model.ModelHealthDetail
import com.teamx.hatlyUser.ui.fragments.hatlymart.stores.model.ModelAllStores
import retrofit2.Response
import retrofit2.http.*


interface ApiService {
    @POST(NetworkCallPoints.SIGN_UP)
    suspend fun signup(@Body params: JsonObject?): Response<ModelSignUp>

    @POST(NetworkCallPoints.VERIFY_SIGNUP_OTP)
    suspend fun verifySignupOtp(@Body params: JsonObject?): Response<ModelLogin>

    @POST(NetworkCallPoints.VERIFY_FORGOT_PASS)
    suspend fun forgotPassVerifyOtp(@Body params: JsonObject?): Response<ModelVerifyPassOtp>

    @POST(NetworkCallPoints.LOGIN)
    suspend fun login(@Body params: JsonObject?): Response<ModelLogin>

    @POST(NetworkCallPoints.LOGIN_WITH_GOOGLE)
    suspend fun loginWithGoogle(@Body params: JsonObject?): Response<ModelLogin>

    @POST(NetworkCallPoints.FORGOT_PASS)
    suspend fun forgotPass(@Body params: JsonObject?): Response<ModelForgotPass>

    @POST(NetworkCallPoints.UPDATE_PASS)
    suspend fun updatePass(@Body params: JsonObject?): Response<ModelUpdatePass>

    @POST(NetworkCallPoints.RESEND_OTP)
    suspend fun resendOtp(@Body params: JsonObject?): Response<ModelForgotPass>


    @GET(NetworkCallPoints.ALL_HEALTH_LIST)
    suspend fun allHealthAndBeautyStores(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("search") search: String,
        @Header("Authorization") basicCredentials: String = "Bearer $TOKENER",
        @Header("deviceData") deviceString: String = "$DEVICE_TOKEN"
    ): Response<ModelAllStores>

    @GET(NetworkCallPoints.HEALTH_DETAILS)
    suspend fun healthDetail(
        @Path("id") id: String,
        @Header("Authorization") basicCredentials: String = "Bearer $TOKENER",
        @Header("deviceData") deviceString: String = "$DEVICE_TOKEN"
    ): Response<ModelHealthDetail>
}