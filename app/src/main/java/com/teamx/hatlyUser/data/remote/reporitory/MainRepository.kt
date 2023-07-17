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
    suspend fun login(@Body param: JsonObject) = apiService.login(param)
    suspend fun signup(@Body param: JsonObject) = apiService.signup(param)
    suspend fun verifySignupOtp(@Body param: JsonObject) = apiService.verifySignupOtp(param)
    suspend fun forgotPass(@Body param: JsonObject) = apiService.forgotPass(param)
    suspend fun forgotPassVerifyOtp(@Body param: JsonObject) = apiService.forgotPassVerifyOtp(param)
    suspend fun updatePass(@Body param: JsonObject) = apiService.updatePass(param)
    suspend fun resendOtp(@Body param: JsonObject) = apiService.resendOtp(param)

    suspend fun allHealthAndBeautyStores(page: Int, limit: Int, search : String) = apiService.allHealthAndBeautyStores(page,limit, search)
    suspend fun healthDetail(id : String) = apiService.healthDetail(id)

}