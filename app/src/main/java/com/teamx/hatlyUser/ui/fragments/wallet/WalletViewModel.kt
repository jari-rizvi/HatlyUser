package com.teamx.hatlyUser.ui.fragments.wallet


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.auth.login.Model.ModelLogin
import com.teamx.hatlyUser.ui.fragments.profile.orderhistory.model.OrderHistoryModel
import com.teamx.hatlyUser.ui.fragments.wallet.model.me.MeModel
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class WalletViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _meResponse = MutableLiveData<Resource<MeModel>>()
    val meResponse: LiveData<Resource<MeModel>>
        get() = _meResponse
    fun me() {
        viewModelScope.launch {
            _meResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.me().let {
                        if (it.isSuccessful) {
                            _meResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _meResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _meResponse.postValue(Resource.error(jsonObj.getString("message")))
//                            _meResponse.postValue(Resource.error(it.message(), null))
                        }
                    }
                } catch (e: Exception) {
                    _meResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _meResponse.postValue(Resource.error("No internet connection", null))
        }
    }

    private val _orderHistoryResponse = MutableLiveData<Resource<OrderHistoryModel>>()
    val orderHistoryResponse: LiveData<Resource<OrderHistoryModel>>
        get() = _orderHistoryResponse

    fun orderHistory(page: Int, limit: Int) {
        viewModelScope.launch {
            _orderHistoryResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.orderHistory(page, limit).let {
                        if (it.isSuccessful) {
                            _orderHistoryResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _orderHistoryResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _orderHistoryResponse.postValue(Resource.error(jsonObj.getString("message")))
                        }
                    }
                } catch (e: Exception) {
                    _orderHistoryResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _orderHistoryResponse.postValue(Resource.error("No internet connection", null))
        }
    }
}