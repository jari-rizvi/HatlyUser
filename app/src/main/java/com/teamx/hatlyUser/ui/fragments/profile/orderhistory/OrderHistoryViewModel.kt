package com.teamx.hatlyUser.ui.fragments.profile.orderhistory


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.payments.cart.model.ModelCart
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

   /* private val _orderHistoryResponse = MutableLiveData<Resource<ModelCart>>()
    val orderHistoryResponse: LiveData<Resource<ModelCart>>
        get() = _orderHistoryResponse

    fun orderHistory() {
        viewModelScope.launch {
            _orderHistoryResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.orderHistory().let {
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
    }*/

}