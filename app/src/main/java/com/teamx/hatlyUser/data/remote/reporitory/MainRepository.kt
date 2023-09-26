package com.teamx.hatlyUser.data.remote.reporitory


import com.google.gson.JsonObject
import com.teamx.hatlyUser.data.local.db.ProductDao
import com.teamx.hatlyUser.data.remote.ApiService
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val apiService: ApiService,
    var localDataSource: ProductDao,
) {
    suspend fun login(@Body param: JsonObject) = apiService.login(param)
    suspend fun loginWithGoogle(@Body param: JsonObject) = apiService.loginWithGoogle(param)
    suspend fun signup(@Body param: JsonObject) = apiService.signup(param)
    suspend fun verifySignupOtp(@Body param: JsonObject) = apiService.verifySignupOtp(param)
    suspend fun forgotPass(@Body param: JsonObject) = apiService.forgotPass(param)
    suspend fun forgotPassVerifyOtp(@Body param: JsonObject) = apiService.forgotPassVerifyOtp(param)
    suspend fun updatePass(@Body param: JsonObject) = apiService.updatePass(param)
    suspend fun resendOtp(@Body param: JsonObject) = apiService.resendOtp(param)

    suspend fun allHealthAndBeautyStores(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("search") search: String,
        @Query("offset") offset: Int,
        @Query("type") type: String
    ) = apiService.allHealthAndBeautyStores(page, limit, search, offset, type)

    suspend fun categoryShop(
        @Query("shopId") shopId: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) = apiService.categoryShop(shopId, page, limit, offset)


    suspend fun storeSubCategory(
        @Query("shopId") shopId: String,
        @Query("category") category: String,
        @Query("search") search: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) = apiService.storeSubCategory(shopId, category, search,page, limit, offset)

    suspend fun popularProducts(
        @Query("shopId") shopId: String
    ) = apiService.popularProducts(shopId)

    suspend fun allFoodsCategories(page: Int, limit: Int, offset: Int) =
        apiService.allFoodsCategories(page, limit, offset)

    suspend fun allFoodsShops(
        page: Int,
        limit: Int,
        offset: Int,
        search: String,
        category: String
    ) = apiService.allFoodsShops(page, limit, offset, search, category)

    suspend fun foodsShopHome(id: String) = apiService.foodsShopHome(id)

    suspend fun prodPreview(id: String) = apiService.prodPreview(id)
    suspend fun addToCart(@Body params: JsonObject) = apiService.addToCart(params)
    suspend fun getCart() = apiService.getCart()
    suspend fun removeCartItem(@Path("id") id: String) = apiService.removeCartItem(id)
    suspend fun updateCartItem(@Body params: JsonObject) = apiService.updateCartItem(params)
    suspend fun checkout(@Body params: JsonObject) = apiService.checkout(params)
    suspend fun orderSummary(@Body params: JsonObject) = apiService.orderSummary(params)
    suspend fun placeOrder(@Body params: JsonObject) = apiService.placeOrder(params)
}
