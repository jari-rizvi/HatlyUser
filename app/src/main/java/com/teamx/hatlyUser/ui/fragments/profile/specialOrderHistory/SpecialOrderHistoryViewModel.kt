package com.teamx.hatlyUser.ui.fragments.profile.specialOrderHistory


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.payments.cart.model.ModelCart
import com.teamx.hatlyUser.ui.fragments.profile.orderhistory.model.OrderHistoryModel
import com.teamx.hatlyUser.ui.fragments.special.specialorder.model.ModelActiveDelieverParcel
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class SpecialOrderHistoryViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _activeDelieverResponse = MutableLiveData<Resource<ModelActiveDelieverParcel>>()
    val activeDelieverResponse: LiveData<Resource<ModelActiveDelieverParcel>>
        get() = _activeDelieverResponse

    fun activeDeliever(
        allDelivered: Boolean,
        page: Int,
        limit: Int,
    ) {
        viewModelScope.launch {
            _activeDelieverResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.activeDeliever(allDelivered, page, limit).let {
                        if (it.isSuccessful) {
                            _activeDelieverResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _activeDelieverResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _activeDelieverResponse.postValue(Resource.error(jsonObj.getString("message")))
                        }
                    }
                } catch (e: Exception) {
                    _activeDelieverResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _activeDelieverResponse.postValue(Resource.error("No internet connection", null))
        }
    }

}