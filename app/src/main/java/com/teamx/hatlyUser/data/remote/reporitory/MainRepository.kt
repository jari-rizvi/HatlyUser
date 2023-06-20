package com.teamx.hatlyUser.data.remote.reporitory


import com.google.gson.JsonObject
import com.teamx.hatlyUser.data.local.db.ProductDao
import com.teamx.hatlyUser.data.remote.ApiService
import retrofit2.http.Body
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val apiService: ApiService,
    var localDataSource: ProductDao,
) {
    suspend fun signup(@Body param: JsonObject) = apiService.signup(param)

    suspend fun verify_otp(@Body param: JsonObject) = apiService.verify_otp(param)

    suspend fun login(@Body param: JsonObject) = apiService.login(param)

    suspend fun forgot(@Body param: JsonObject) = apiService.forgot(param)
    suspend fun createPass(@Body param: JsonObject) = apiService.createPass(param)
    suspend fun resendOtp(@Body param: JsonObject) = apiService.resendOtp(param)

}