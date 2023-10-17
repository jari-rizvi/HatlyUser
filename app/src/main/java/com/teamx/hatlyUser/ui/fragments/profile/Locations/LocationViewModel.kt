package com.teamx.hatlyUser.ui.fragments.profile.Locations


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.location.map.modelDefaultAddress.ModelDefaultAddress
import com.teamx.hatlyUser.ui.fragments.location.map.models.CreateAddressModel
import com.teamx.hatlyUser.ui.fragments.location.map.models.CreateAddressModelItem
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.http.Path
import javax.inject.Inject


@HiltViewModel
class LocationViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {


    private val _getAllAddressResponse = MutableLiveData<Resource<CreateAddressModel>>()
    val getAlAddressResponse: LiveData<Resource<CreateAddressModel>>
        get() = _getAllAddressResponse

    fun getAlAddress() {
        viewModelScope.launch {
            _getAllAddressResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.getAddress().let {
                        if (it.isSuccessful) {
                            _getAllAddressResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _getAllAddressResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _getAllAddressResponse.postValue(Resource.error(jsonObj.getString("message")))
//                            _getAllAddressResponse.postValue(Resource.error(it.message(), null))
                        }
                    }
                } catch (e: Exception) {
                    _getAllAddressResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _getAllAddressResponse.postValue(Resource.error("No internet connection", null))
        }
    }


    private val _setDefaultAddressResponse = MutableLiveData<Resource<ModelDefaultAddress>>()
    val setDefaultAddressResponse: LiveData<Resource<ModelDefaultAddress>>
        get() = _setDefaultAddressResponse

    fun setDefaultAddress(id: String,) {
        viewModelScope.launch {
            _setDefaultAddressResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.setDefaultAddress(id).let {
                        if (it.isSuccessful) {
                            _setDefaultAddressResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _setDefaultAddressResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _setDefaultAddressResponse.postValue(Resource.error(jsonObj.getString("message")))
//                            _setDefaultAddressResponse.postValue(Resource.error(it.message(), null))
                        }
                    }
                } catch (e: Exception) {
                    _setDefaultAddressResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _setDefaultAddressResponse.postValue(Resource.error("No internet connection", null))
        }
    }


}