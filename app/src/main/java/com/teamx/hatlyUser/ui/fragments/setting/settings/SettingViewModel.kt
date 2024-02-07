package com.teamx.hatlyUser.ui.fragments.setting.settings


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.auth.forgotpassword.model.ModelForgotPass
import com.teamx.hatlyUser.ui.fragments.home.model.FcmModel
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class SettingViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {


    private val _pushNotificationResponse = MutableLiveData<Resource<ModelForgotPass>>()
    val pushNotification: LiveData<Resource<ModelForgotPass>>
        get() = _pushNotificationResponse

    fun pushNotification(param: JsonObject) {
        viewModelScope.launch {
            _pushNotificationResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.pushNotification(param).let {
                        if (it.isSuccessful) {
                            _pushNotificationResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _pushNotificationResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else if (it.code() == 401) {
                            _pushNotificationResponse.postValue(Resource.unAuth("", null))
                        }else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _pushNotificationResponse.postValue(Resource.error(jsonObj.getString("message")))
//                            _pushNotificationResponse.postValue(Resource.error(it.message(), null))
                        }
                    }
                } catch (e: Exception) {
                    _pushNotificationResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _pushNotificationResponse.postValue(Resource.error("No internet connection", null))
        }
    }

}