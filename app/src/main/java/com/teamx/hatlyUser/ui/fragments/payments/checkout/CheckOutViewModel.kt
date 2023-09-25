package com.teamx.hatlyUser.ui.fragments.payments.checkout


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.payments.cart.model.ModelCart
import com.teamx.hatlyUser.ui.fragments.payments.checkout.model.ModelOrderSummary
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class CheckOutViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _checkoutResponse = MutableLiveData<Resource<ModelOrderSummary>>()
    val checkoutResponse: LiveData<Resource<ModelOrderSummary>>
        get() = _checkoutResponse

    fun checkout(params: JsonObject) {
        viewModelScope.launch {
            _checkoutResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.checkout(params).let {
                        if (it.isSuccessful) {
                            _checkoutResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _checkoutResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _checkoutResponse.postValue(Resource.error(jsonObj.getString("message")))
                        }
                    }
                } catch (e: Exception) {
                    _checkoutResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _checkoutResponse.postValue(Resource.error("No internet connection", null))
        }
    }

    private val _orderSummaryResponse = MutableLiveData<Resource<ModelOrderSummary>>()
    val orderSummaryResponse: LiveData<Resource<ModelOrderSummary>>
        get() = _orderSummaryResponse

    fun orderSummary(params: JsonObject) {
        viewModelScope.launch {
            _orderSummaryResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.orderSummary(params).let {
                        if (it.isSuccessful) {
                            _orderSummaryResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _orderSummaryResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _orderSummaryResponse.postValue(Resource.error(jsonObj.getString("message")))
                        }
                    }
                } catch (e: Exception) {
                    _orderSummaryResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _orderSummaryResponse.postValue(Resource.error("No internet connection", null))
        }
    }

}