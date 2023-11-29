package com.teamx.hatlyUser.ui.fragments.auth.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.auth.login.Model.ModelLogin
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _login = MutableLiveData<Resource<ModelLogin>>()
    val loginResponse: LiveData<Resource<ModelLogin>>
        get() = _login

    fun login(param: JsonObject) {
        viewModelScope.launch {
            _login.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.login(param).let {
                        if (it.isSuccessful) {
                            _login.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _login.postValue(Resource.error(jsonObj.getString("message")))
                        } else if (it.code() == 401) {
                            _login.postValue(Resource.unAuth("", null))
                        }else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _login.postValue(Resource.error(jsonObj.getString("message")))
//                            _login.postValue(Resource.error(it.message(), null))
                        }
                    }
                } catch (e: Exception) {
                    _login.postValue(Resource.error("${e.message}", null))
                }
            } else _login.postValue(Resource.error("No internet connection", null))
        }
    }

    private val _loginWithGoogle = MutableLiveData<Resource<ModelLogin>>()
    val loginWithGoogleResponse: LiveData<Resource<ModelLogin>>
        get() = _loginWithGoogle
    fun loginWithGoogle(param: JsonObject) {
        viewModelScope.launch {
            _loginWithGoogle.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.loginWithGoogle(param).let {
                        if (it.isSuccessful) {
                            _loginWithGoogle.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _loginWithGoogle.postValue(Resource.error(jsonObj.getString("message")))
                        } else if (it.code() == 401) {
                            _loginWithGoogle.postValue(Resource.unAuth("", null))
                        }else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _loginWithGoogle.postValue(Resource.error(jsonObj.getString("message")))
//                            _loginWithGoogle.postValue(Resource.error(it.message(), null))
                        }
                    }
                } catch (e: Exception) {
                    _loginWithGoogle.postValue(Resource.error("${e.message}", null))
                }
            } else _loginWithGoogle.postValue(Resource.error("No internet connection", null))
        }
    }
}