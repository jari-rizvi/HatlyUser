package com.teamx.hatlyUser.ui.fragments.products

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.auth.forgotpassword.model.ModelForgotPass
import com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.modelShopHome.FoodShopModel
import com.teamx.hatlyUser.ui.fragments.payments.cart.model.ModelCart
import com.teamx.hatlyUser.ui.fragments.products.model.ModelProductPreview
import com.teamx.hatlyUser.ui.fragments.products.modelAddToCart.AddToCart
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.http.Body
import javax.inject.Inject


@HiltViewModel
class ProductPreviewViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {


    private val _prodPreviewHome = MutableLiveData<Resource<ModelProductPreview>>()
    val prodPreviewResponse: LiveData<Resource<ModelProductPreview>>
        get() = _prodPreviewHome

    fun prodPreview(id:String) {
        viewModelScope.launch {
            _prodPreviewHome.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.prodPreview(id).let {
                        if (it.isSuccessful) {
                            _prodPreviewHome.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _prodPreviewHome.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _prodPreviewHome.postValue(Resource.error(jsonObj.getString("message")))
                        }
                    }
                } catch (e: Exception) {
                    _prodPreviewHome.postValue(Resource.error("${e.message}", null))
                }
            } else _prodPreviewHome.postValue(Resource.error("No internet connection", null))
        }
    }


    private val _addToCartResponse = MutableLiveData<Resource<AddToCart>>()
    val addToCartResponse: LiveData<Resource<AddToCart>>
        get() = _addToCartResponse

    fun addToCart(params: JsonObject) {
        viewModelScope.launch {
            _addToCartResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.addToCart(params).let {
                        if (it.isSuccessful) {
                            _addToCartResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _addToCartResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _addToCartResponse.postValue(Resource.error(jsonObj.getString("message")))
                        }
                        Log.d("addToCartResponse", "addToCart: ${it.code()}")
                    }
                } catch (e: Exception) {
                    _addToCartResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _addToCartResponse.postValue(Resource.error("No internet connection", null))
        }
    }

    private val _emptyCartResponse = MutableLiveData<Resource<ModelForgotPass>>()
    val emptyCartResponse: LiveData<Resource<ModelForgotPass>>
        get() = _emptyCartResponse

    fun emptyCart() {
        viewModelScope.launch {
            _emptyCartResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.emptyCart().let {
                        if (it.isSuccessful) {
                            _emptyCartResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _emptyCartResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _emptyCartResponse.postValue(Resource.error(jsonObj.getString("message")))
                        }
                        Log.d("addToCartResponse", "addToCart: ${it.code()}")
                    }
                } catch (e: Exception) {
                    _emptyCartResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _emptyCartResponse.postValue(Resource.error("No internet connection", null))
        }
    }


    private val _updateCartItemResponse = MutableLiveData<Resource<ModelCart>>()
    val updateCartItemResponse: LiveData<Resource<ModelCart>>
        get() = _updateCartItemResponse

    fun updateCartItem(params: JsonObject) {
        viewModelScope.launch {
            _updateCartItemResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.updateCartItem(params).let {
                        if (it.isSuccessful) {
                            _updateCartItemResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _updateCartItemResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _updateCartItemResponse.postValue(Resource.error(jsonObj.getString("message")))
                        }
                    }
                } catch (e: Exception) {
                    _updateCartItemResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _updateCartItemResponse.postValue(Resource.error("No internet connection", null))
        }
    }

}