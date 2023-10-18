package com.teamx.hatlyUser.data.remote

import com.google.gson.JsonObject
import com.teamx.hatlyUser.constants.NetworkCallPoints
import com.teamx.hatlyUser.constants.NetworkCallPointsNest.Companion.DEVICE_TOKEN
import com.teamx.hatlyUser.constants.NetworkCallPointsNest.Companion.TOKENER
import com.teamx.hatlyUser.ui.fragments.auth.createpassword.model.ModelUpdatePass
import com.teamx.hatlyUser.ui.fragments.auth.forgotpassword.model.ModelForgotPass
import com.teamx.hatlyUser.ui.fragments.auth.login.Model.Location
import com.teamx.hatlyUser.ui.fragments.auth.login.Model.ModelLogin
import com.teamx.hatlyUser.ui.fragments.auth.otp.model.ModelVerifyPassOtp
import com.teamx.hatlyUser.ui.fragments.auth.signup.model.ModelSignUp
import com.teamx.hatlyUser.ui.fragments.foods.FoodsHome.models.modelCategory.ModelFoodsCategory
import com.teamx.hatlyUser.ui.fragments.foods.FoodsHome.models.modelShops.ModelFoodShops
import com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.modelShopHome.FoodShopModel
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.model.categoryModel.ModelCategory
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.model.popularproductmodel.ModelPopularProducts
import com.teamx.hatlyUser.ui.fragments.hatlymart.stores.model.ModelAllStores
import com.teamx.hatlyUser.ui.fragments.location.map.modelDefaultAddress.ModelDefaultAddress
import com.teamx.hatlyUser.ui.fragments.location.map.models.CreateAddressModel
import com.teamx.hatlyUser.ui.fragments.payments.cart.model.ModelCart
import com.teamx.hatlyUser.ui.fragments.payments.checkout.model.ModelOrderSummary
import com.teamx.hatlyUser.ui.fragments.payments.checkout.modelPlaceOrder.ModelPlaceOrder
import com.teamx.hatlyUser.ui.fragments.payments.paymentmethod.defaultmodel.ModelDefaultCredCards
import com.teamx.hatlyUser.ui.fragments.payments.paymentmethod.modelDetach.ModelDetachCredCards
import com.teamx.hatlyUser.ui.fragments.payments.paymentmethod.modelGetCards.ModelCredCards
import com.teamx.hatlyUser.ui.fragments.products.model.ModelProductPreview
import com.teamx.hatlyUser.ui.fragments.products.modelAddToCart.AddToCart
import com.teamx.hatlyUser.ui.fragments.profile.orderdetail.modelUploadImages.ModelUploadImages
import com.teamx.hatlyUser.ui.fragments.profile.orderhistory.model.OrderHistoryModel
import com.teamx.hatlyUser.ui.fragments.shophome.model.ModelSubCategoryStore
import okhttp3.MultipartBody
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


    @GET(NetworkCallPoints.ALL_SHOPS)
    suspend fun allHealthAndBeautyStores(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("search") search: String,
        @Query("offset") offset: Int,
        @Query("type") type: String,
        @Header("Authorization") basicCredentials: String = "Bearer $TOKENER"
    ): Response<ModelAllStores>

    @GET(NetworkCallPoints.SHOP_CATEGOIES)
    suspend fun categoryShop(
        @Query("shopId") shopId: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Header("Authorization") basicCredentials: String = "Bearer $TOKENER",
        @Header("deviceData") deviceString: String = "$DEVICE_TOKEN"
    ): Response<ModelCategory>

    @GET(NetworkCallPoints.POPULAR_PRODUCTS)
    suspend fun popularProducts(
        @Query("shopId") shopId: String,
        @Header("Authorization") basicCredentials: String = "Bearer $TOKENER",
        @Header("deviceData") deviceString: String = "$DEVICE_TOKEN"
    ): Response<ModelPopularProducts>


    @GET(NetworkCallPoints.SHOP_SUB_CATEGOIES)
    suspend fun storeSubCategory(
        @Query("shopId") shopId: String,
        @Query("category") category: String,
        @Query("search") search: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Header("Authorization") basicCredentials: String = "Bearer $TOKENER",
        @Header("deviceData") deviceString: String = "$DEVICE_TOKEN"
    ): Response<ModelSubCategoryStore>


    @GET(NetworkCallPoints.ALL_FOODS_CATEGORIES)
    suspend fun allFoodsCategories(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Header("Authorization") basicCredentials: String = "Bearer $TOKENER"
    ): Response<ModelFoodsCategory>

    @GET(NetworkCallPoints.ALL_FOODS_SHOPS)
    suspend fun allFoodsShops(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("search") search: String,
        @Query("category") id: String?,
        @Header("Authorization") basicCredentials: String = "Bearer $TOKENER",
        @Header("deviceData") deviceString: String = "$DEVICE_TOKEN"
    ): Response<ModelFoodShops>

    @GET(NetworkCallPoints.SHOP_FOODS)
    suspend fun foodsShopHome(
        @Path("id") id: String,
        @Header("Authorization") basicCredentials: String = "Bearer $TOKENER",
        @Header("deviceData") deviceString: String = "$DEVICE_TOKEN"
    ): Response<FoodShopModel>

    @GET(NetworkCallPoints.PROD_PREVIEW)
    suspend fun prodPreview(
        @Path("id") id: String,
        @Header("Authorization") basicCredentials: String = "Bearer $TOKENER",
        @Header("deviceData") deviceString: String = "$DEVICE_TOKEN"
    ): Response<ModelProductPreview>

    @POST(NetworkCallPoints.ADD_TO_CART)
    suspend fun addToCart(
        @Body params: JsonObject,
        @Header("Authorization") basicCredentials: String = "Bearer $TOKENER",
        @Header("deviceData") deviceString: String = "$DEVICE_TOKEN"
    ): Response<AddToCart>

    @GET(NetworkCallPoints.GET_ALL_CARTS)
    suspend fun getCart(
        @Header("Authorization") basicCredentials: String = "Bearer $TOKENER",
        @Header("deviceData") deviceString: String = "$DEVICE_TOKEN"
    ): Response<ModelCart>

    @GET(NetworkCallPoints.REMOVE_CART_ITEM)
    suspend fun removeCartItem(
        @Path("id") id: String,
        @Header("Authorization") basicCredentials: String = "Bearer $TOKENER",
        @Header("deviceData") deviceString: String = "$DEVICE_TOKEN"
    ): Response<AddToCart>

    @POST(NetworkCallPoints.UPDATE_CART_ITEM)
    suspend fun updateCartItem(
        @Body params: JsonObject,
        @Header("Authorization") basicCredentials: String = "Bearer $TOKENER",
        @Header("deviceData") deviceString: String = "$DEVICE_TOKEN"
    ): Response<ModelCart>

    @POST(NetworkCallPoints.CHECKOUT)
    suspend fun checkout(
        @Body params: JsonObject,
        @Header("Authorization") basicCredentials: String = "Bearer $TOKENER",
        @Header("deviceData") deviceString: String = "$DEVICE_TOKEN"
    ): Response<ModelOrderSummary>

    @POST(NetworkCallPoints.ORDER_SUMMARY)
    suspend fun orderSummary(
        @Body params: JsonObject,
        @Header("Authorization") basicCredentials: String = "Bearer $TOKENER",
        @Header("deviceData") deviceString: String = "$DEVICE_TOKEN"
    ): Response<ModelOrderSummary>

    @POST(NetworkCallPoints.PLACE_ORDER)
    suspend fun placeOrder(
        @Body params: JsonObject,
        @Header("Authorization") basicCredentials: String = "Bearer $TOKENER",
        @Header("deviceData") deviceString: String = "$DEVICE_TOKEN"
    ): Response<ModelPlaceOrder>

    @GET(NetworkCallPoints.ORDER_HISTORY)
    suspend fun orderHistory(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Header("Authorization") basicCredentials: String = "Bearer $TOKENER",
        @Header("deviceData") deviceString: String = "$DEVICE_TOKEN"
    ): Response<OrderHistoryModel>

    @Multipart
    @POST(NetworkCallPoints.UPLOAD_REVIEW_IMGS)
    suspend fun uploadReviewImg(
        @Part images: List<MultipartBody.Part>,
        @Header("Authorization") basicCredentials: String = "Bearer $TOKENER",
        @Header("deviceData") deviceString: String = "$DEVICE_TOKEN"
    ): Response<ModelUploadImages>

    @GET(NetworkCallPoints.CREDS_CARDS)
    suspend fun getCredCards(
        @Header("Authorization") basicCredentials: String = "Bearer $TOKENER",
        @Header("deviceData") deviceString: String = "$DEVICE_TOKEN"
    ): Response<ModelCredCards>

    @POST(NetworkCallPoints.DEFAULT_CREDS_CARDS)
    suspend fun setDefaultCredCards(
        @Body params: JsonObject,
        @Header("Authorization") basicCredentials: String = "Bearer $TOKENER",
        @Header("deviceData") deviceString: String = "$DEVICE_TOKEN"
    ): Response<ModelDefaultCredCards>

    @POST(NetworkCallPoints.DETACH_CREDS_CARDS)
    suspend fun setDetachCredCards(
        @Body params: JsonObject,
        @Header("Authorization") basicCredentials: String = "Bearer $TOKENER",
        @Header("deviceData") deviceString: String = "$DEVICE_TOKEN"
    ): Response<ModelDetachCredCards>

    @POST(NetworkCallPoints.CREATE_ADDRESS)
    suspend fun createAddress(
        @Body params: JsonObject,
        @Header("Authorization") basicCredentials: String = "Bearer $TOKENER",
        @Header("deviceData") deviceString: String = "$DEVICE_TOKEN"
    ): Response<Location>

    @PUT(NetworkCallPoints.UPDATE_ADDRESS)
    suspend fun updateAddress(
        @Path("id") id: String,
        @Body params: JsonObject,
        @Header("Authorization") basicCredentials: String = "Bearer $TOKENER",
        @Header("deviceData") deviceString: String = "$DEVICE_TOKEN"
    ): Response<Location>

    @GET(NetworkCallPoints.CREATE_ADDRESS)
    suspend fun getAddress(
        @Header("Authorization") basicCredentials: String = "Bearer $TOKENER",
        @Header("deviceData") deviceString: String = "$DEVICE_TOKEN"
    ): Response<CreateAddressModel>


    @PUT(NetworkCallPoints.SET_DEFAULT_ADDRESS)
    suspend fun setDefaultAddress(
        @Path("id") id: String,
        @Header("Authorization") basicCredentials: String = "Bearer $TOKENER",
        @Header("deviceData") deviceString: String = "$DEVICE_TOKEN"
    ): Response<ModelDefaultAddress>
}