package com.teamx.hatlyUser.ui.fragments.special.specialorder


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.payments.checkout.modelPlaceOrder.ModelPlaceOrder
import com.teamx.hatlyUser.ui.fragments.profile.specialOrderHistory.model.Doc
import com.teamx.hatlyUser.ui.fragments.profile.specialOrderHistory.model.ModelSpecialHistory
import com.teamx.hatlyUser.ui.fragments.special.specialorder.model.ModelActiveDelieverParcel
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.http.Query
import javax.inject.Inject


@HiltViewModel
class SpecialOrderViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {


    private val _activeResponse = MutableLiveData<Resource<Doc>>()
    val activeResponse: LiveData<Resource<Doc>>
        get() = _activeResponse

    fun activeDeliever() {
        viewModelScope.launch {
            _activeResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.activeDeliever().let {
                        if (it.isSuccessful) {
                            _activeResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _activeResponse.postValue(Resource.error(jsonObj.getString("message")))
                        }else if (it.code() == 401) {
                            _activeResponse.postValue(Resource.unAuth("", null))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _activeResponse.postValue(Resource.error(jsonObj.getString("message")))
                        }
                    }
                } catch (e: Exception) {
                    _activeResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _activeResponse.postValue(Resource.error("No internet connection", null))
        }
    }

    private val _allParcelResponse = MutableLiveData<Resource<ModelSpecialHistory>>()
    val allParcelResponse: LiveData<Resource<ModelSpecialHistory>>
        get() = _allParcelResponse

    fun allParcel(
        status: String,
        limit: Int,
        page: Int,
    ) {
        viewModelScope.launch {
            _allParcelResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.allParcel(status, page, limit).let {
                        if (it.isSuccessful) {
                            _allParcelResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _allParcelResponse.postValue(Resource.error(jsonObj.getString("message")))
                        }else if (it.code() == 401) {
                            _allParcelResponse.postValue(Resource.unAuth("", null))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _allParcelResponse.postValue(Resource.error(jsonObj.getString("message")))
                        }
                    }
                } catch (e: Exception) {
                    _allParcelResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _allParcelResponse.postValue(Resource.error("No internet connection", null))
        }
    }

}