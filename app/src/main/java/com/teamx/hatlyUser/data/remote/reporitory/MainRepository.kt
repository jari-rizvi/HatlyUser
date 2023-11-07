package com.teamx.hatlyUser.data.remote.reporitory


import com.google.gson.JsonObject
import com.teamx.hatlyUser.data.local.db.ProductDao
import com.teamx.hatlyUser.data.remote.ApiService
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val apiService: ApiService,
    var localDataSource: ProductDao,
) {
    suspend fun login(@Body param: JsonObject) = apiService.login(param)
    suspend fun fcm(@Body param: JsonObject) = apiService.fcm(param)
    suspend fun homeSearch(
        @Query("search") search: String,
        @Query("categorry") categorry: String,
        @Query("type") type: String,
        @Query("limit") limit: Int,
        @Query("page") page: Int,
    ) = apiService.homeSearch(search, categorry, type, limit, page)

    suspend fun loginWithGoogle(@Body param: JsonObject) = apiService.loginWithGoogle(param)
    suspend fun me() = apiService.me()
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
    ) = apiService.storeSubCategory(shopId, category, search, page, limit, offset)

    suspend fun popularProducts(
        @Query("shopId") shopId: String
    ) = apiService.popularProducts(shopId)

    suspend fun allFoodsCategories(page: Int, limit: Int) =
        apiService.allFoodsCategories(page, limit)

    suspend fun allFoodsShops(
        page: Int,
        limit: Int,
        offset: Int,
        search: String,
        id: String?
    ) = apiService.allFoodsShops(page, limit, offset, search, id)

    suspend fun foodsShopHome(id: String) = apiService.foodsShopHome(id)
    suspend fun favRemove(params: JsonObject) = apiService.favRemove(params)

    suspend fun notification() = apiService.notification()

    suspend fun prodPreview(id: String) = apiService.prodPreview(id)
    suspend fun addToCart(@Body params: JsonObject) = apiService.addToCart(params)
    suspend fun getCart() = apiService.getCart()
    suspend fun removeCartItem(@Path("id") id: String) = apiService.removeCartItem(id)
    suspend fun updateCartItem(@Body params: JsonObject) = apiService.updateCartItem(params)
    suspend fun checkout(@Body params: JsonObject) = apiService.checkout(params)
    suspend fun orderSummary(@Body params: JsonObject) = apiService.orderSummary(params)
    suspend fun fareCalculation(@Body params: JsonObject) = apiService.fareCalculation(params)
    suspend fun createParcel(@Body params: JsonObject) = apiService.createParcel(params)
    suspend fun placeOrder(@Body params: JsonObject) = apiService.placeOrder(params)
    suspend fun activeDeliever(
        @Query("allDelivered") allDelivered: Boolean,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ) = apiService.activeDeliever(allDelivered, page, limit)

    suspend fun orderHistory(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ) = apiService.orderHistory(page, limit)

    suspend fun uploadReviewImg(
        @Part images: List<MultipartBody.Part>
    ) = apiService.uploadReviewImg(images)

    suspend fun updateProfile(
        @Body params: JsonObject,
    ) = apiService.updateProfile(params)

    suspend fun reviewList(
        @Query("shopId") shopId: String,
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ) = apiService.reviewList(shopId, limit, page)

    suspend fun reviewOrder(
        @Body params: JsonObject
    ) = apiService.reviewOrder(params)

    suspend fun wishList(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ) = apiService.wishList(limit, page)

    suspend fun reOrder(
        id: String
    ) = apiService.reOrder(id)

    suspend fun cancelOrder(
        id: String
    ) = apiService.cancelOrder(id)

    suspend fun getCredCards(
    ) = apiService.getCredCards()

    suspend fun topUpSaved(
        @Body params: JsonObject
    ) = apiService.topUpSaved(params)

    suspend fun setDefaultCredCards(
        @Body params: JsonObject
    ) = apiService.setDefaultCredCards(params)

    suspend fun setDetachCredCards(
        @Body params: JsonObject
    ) = apiService.setDetachCredCards(params)

    suspend fun createAddress(
        @Body params: JsonObject
    ) = apiService.createAddress(params)

    suspend fun updateAddress(
        @Path("id") id: String,
        @Body params: JsonObject
    ) = apiService.updateAddress(id, params)

    suspend fun getAddress() = apiService.getAddress()
    suspend fun changePassword(@Body params: JsonObject) = apiService.changePassword(params)

    suspend fun setDefaultAddress(@Path("id") id: String) = apiService.setDefaultAddress(id)
    suspend fun deleteAddress(@Path("id") id: String) = apiService.deleteAddress(id)
}
