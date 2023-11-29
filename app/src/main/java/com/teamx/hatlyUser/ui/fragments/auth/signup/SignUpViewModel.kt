package com.teamx.hatlyUser.ui.fragments.auth.signup


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.auth.signup.model.ModelSignUp
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {



    private val _signupResponse = MutableLiveData<Resource<ModelSignUp>>()
    val signupResponse: LiveData<Resource<ModelSignUp>>
        get() = _signupResponse

    fun signup(param: JsonObject) {
        viewModelScope.launch {
            _signupResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.signup(param).let {
                        if (it.isSuccessful) {
                            _signupResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _signupResponse.postValue(Resource.error(jsonObj.getString("message")))
                        }else if (it.code() == 401) {
                            _signupResponse.postValue(Resource.unAuth("", null))
                        } else {
                            _signupResponse.postValue(Resource.error("Some thing went wrong", null))
                        }
                    }
                } catch (e: Exception) {
                    _signupResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _signupResponse.postValue(Resource.error("No internet connection", null))
        }
    }

}