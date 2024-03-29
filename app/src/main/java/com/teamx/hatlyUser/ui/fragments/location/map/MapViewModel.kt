package com.teamx.hatlyUser.ui.fragments.location.map


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.auth.login.Model.Location
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _createAddressResponse = MutableLiveData<Resource<Location>>()
    val createAddressResponse: LiveData<Resource<Location>>
        get() = _createAddressResponse

    fun createAddress(param: JsonObject) {
        viewModelScope.launch {
            _createAddressResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.createAddress(param).let {
                        if (it.isSuccessful) {
                            _createAddressResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _createAddressResponse.postValue(Resource.error(jsonObj.getString("message")))
                        }else if (it.code() == 401) {
                            _createAddressResponse.postValue(Resource.unAuth("", null))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _createAddressResponse.postValue(Resource.error(jsonObj.getString("message")))
//                            _createAddressResponse.postValue(Resource.error(it.message(), null))
                        }
                    }
                } catch (e: Exception) {
                    _createAddressResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _createAddressResponse.postValue(Resource.error("No internet connection", null))
        }
    }

    private val _updateAddressResponse = MutableLiveData<Resource<Location>>()
    val updateAddressResponse: LiveData<Resource<Location>>
        get() = _updateAddressResponse

    fun updateAddress(id:String, param: JsonObject) {
        viewModelScope.launch {
            _updateAddressResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.updateAddress(id,param).let {
                        if (it.isSuccessful) {
                            _updateAddressResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _updateAddressResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else if (it.code() == 401) {
                            _updateAddressResponse.postValue(Resource.unAuth("", null))
                        }else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _updateAddressResponse.postValue(Resource.error(jsonObj.getString("message")))
//                            _updateAddressResponse.postValue(Resource.error(it.message(), null))
                        }
                    }
                } catch (e: Exception) {
                    _updateAddressResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _updateAddressResponse.postValue(Resource.error("No internet connection", null))
        }
    }


}