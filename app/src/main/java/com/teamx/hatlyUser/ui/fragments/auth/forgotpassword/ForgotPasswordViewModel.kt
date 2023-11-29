package com.teamx.hatlyUser.ui.fragments.auth.forgotpassword


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.auth.forgotpassword.model.ModelForgotPass
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _forgotPassResponse = MutableLiveData<Resource<ModelForgotPass>>()
    val forgotPassResponse: LiveData<Resource<ModelForgotPass>>
        get() = _forgotPassResponse

    fun forgotPass(param: JsonObject) {
        viewModelScope.launch {
            _forgotPassResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.forgotPass(param).let {
                        if (it.isSuccessful) {
                            _forgotPassResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _forgotPassResponse.postValue(Resource.error(jsonObj.getString("message")))
                        }else if (it.code() == 401) {
                            _forgotPassResponse.postValue(Resource.unAuth("", null))
                        } else {
                            _forgotPassResponse.postValue(Resource.error("Some thing went wrong", null))
                        }
                    }
                } catch (e: Exception) {
                    _forgotPassResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _forgotPassResponse.postValue(Resource.error("No internet connection", null))
        }
    }

}