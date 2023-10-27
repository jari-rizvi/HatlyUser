package com.teamx.hatlyUser.ui.fragments.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.auth.login.Model.ModelLogin
import com.teamx.hatlyUser.ui.fragments.home.model.FcmModel
import com.teamx.hatlyUser.ui.fragments.location.map.modelDefaultAddress.ModelDefaultAddress
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _fcmResponse = MutableLiveData<Resource<FcmModel>>()
    val fcmResponse: LiveData<Resource<FcmModel>>
        get() = _fcmResponse

    fun fcm(param: JsonObject) {
        viewModelScope.launch {
            _fcmResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.fcm(param).let {
                        if (it.isSuccessful) {
                            _fcmResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _fcmResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _fcmResponse.postValue(Resource.error(jsonObj.getString("message")))
//                            _fcmResponse.postValue(Resource.error(it.message(), null))
                        }
                    }
                } catch (e: Exception) {
                    _fcmResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _fcmResponse.postValue(Resource.error("No internet connection", null))
        }
    }
}