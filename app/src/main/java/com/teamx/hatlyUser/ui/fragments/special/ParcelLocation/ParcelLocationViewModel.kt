package com.teamx.hatlyUser.ui.fragments.special.ParcelLocation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.home.model.FcmModel
import com.teamx.hatlyUser.ui.fragments.special.ParcelLocation.model.fare.ModelFare
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class ParcelLocationViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _fareCalculationResponse = MutableLiveData<Resource<ModelFare>>()
    val fareCalculationResponse: LiveData<Resource<ModelFare>>
        get() = _fareCalculationResponse

    fun fareCalculation(params: JsonObject) {
        viewModelScope.launch {
            _fareCalculationResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.fareCalculation(params).let {
                        if (it.isSuccessful) {
                            _fareCalculationResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _fareCalculationResponse.postValue(Resource.error(jsonObj.getString("message")))
                        }else if (it.code() == 401) {
                            _fareCalculationResponse.postValue(Resource.unAuth("", null))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _fareCalculationResponse.postValue(Resource.error(jsonObj.getString("message")))
                        }
                    }
                } catch (e: Exception) {
                    _fareCalculationResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _fareCalculationResponse.postValue(Resource.error("No internet connection", null))
        }
    }

    private val _createParcelResponse = MutableLiveData<Resource<FcmModel>>()
    val createParcelResponse: LiveData<Resource<FcmModel>>
        get() = _createParcelResponse

    fun createParcel(params: JsonObject) {
        viewModelScope.launch {
            _createParcelResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.createParcel(params).let {
                        if (it.isSuccessful) {
                            _createParcelResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _createParcelResponse.postValue(Resource.error(jsonObj.getString("message")))
                        }else if (it.code() == 401) {
                            _createParcelResponse.postValue(Resource.unAuth("", null))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _createParcelResponse.postValue(Resource.error(jsonObj.getString("message")))
                        }
                    }
                } catch (e: Exception) {
                    _createParcelResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _createParcelResponse.postValue(Resource.error("No internet connection", null))
        }
    }

}