package com.teamx.hatlyUser.ui.fragments.payments.cart


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.payments.cart.model.ModelCart
import com.teamx.hatlyUser.ui.fragments.products.model.ModelProductPreview
import com.teamx.hatlyUser.ui.fragments.products.modelAddToCart.AddToCart
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.http.Path
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _getCartResponse = MutableLiveData<Resource<ModelCart>>()
    val getCartResponse: LiveData<Resource<ModelCart>>
        get() = _getCartResponse

    fun getCart() {
        viewModelScope.launch {
            _getCartResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.getCart().let {
                        if (it.isSuccessful) {
                            _getCartResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _getCartResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _getCartResponse.postValue(Resource.error(jsonObj.getString("message")))
                        }
                    }
                } catch (e: Exception) {
                    _getCartResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _getCartResponse.postValue(Resource.error("No internet connection", null))
        }
    }


    private val _removeCartItemResponse = MutableLiveData<Resource<AddToCart>>()
    val removeCartItemResponse: LiveData<Resource<AddToCart>>
        get() = _removeCartItemResponse

    fun removeCartItem(id: String) {
        viewModelScope.launch {
            _removeCartItemResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.removeCartItem(id).let {
                        if (it.isSuccessful) {
                            _removeCartItemResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _removeCartItemResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _removeCartItemResponse.postValue(Resource.error(jsonObj.getString("message")))
                        }
                    }
                } catch (e: Exception) {
                    _removeCartItemResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _removeCartItemResponse.postValue(Resource.error("No internet connection", null))
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