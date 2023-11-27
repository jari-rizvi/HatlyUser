package com.teamx.hatlyUser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.products.modelAddToCart.AddToCart
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


/**
 * Shared View Model class for sharing data between fragments
 */
@HiltViewModel
class SharedViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
): BaseViewModel() {

//    private val _addToCartResponse = MutableLiveData<Resource<AddToCart>>()
//    val addToCartResponse: LiveData<Resource<AddToCart>>
//        get() = _addToCartResponse
//
//    fun addToCart(params: JsonObject) {
//        viewModelScope.launch {
//            _addToCartResponse.postValue(Resource.loading(null))
//            if (networkHelper.isNetworkConnected()) {
//                try {
//                    mainRepository.addToCart(params).let {
//                        if (it.isSuccessful) {
//                            _addToCartResponse.postValue(Resource.success(it.body()!!))
//                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
//                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
//                            _addToCartResponse.postValue(Resource.error(jsonObj.getString("message")))
//                        } else {
//                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
//                            _addToCartResponse.postValue(Resource.error(jsonObj.getString("message")))
//                        }
//                        Log.d("addToCartResponse", "addToCart: ${it.code()}")
//                    }
//                } catch (e: Exception) {
//                    _addToCartResponse.postValue(Resource.error("${e.message}", null))
//                }
//            } else _addToCartResponse.postValue(Resource.error("No internet connection", null))
//        }
//    }


}