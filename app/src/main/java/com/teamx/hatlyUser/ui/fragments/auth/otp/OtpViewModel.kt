package com.teamx.hatlyUser.ui.fragments.auth.otp


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.auth.otp.model.ModelVerifyOtp
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class OtpViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {


    private val _verify_otp = MutableLiveData<Resource<ModelVerifyOtp>>()
    val verifyOtpResponse: LiveData<Resource<ModelVerifyOtp>>
        get() = _verify_otp

    fun verifyOtp(param: JsonObject) {
        viewModelScope.launch {
            _verify_otp.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.verify_otp(param).let {
                        if (it.isSuccessful) {
                            _verify_otp.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _verify_otp.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _verify_otp.postValue(Resource.error("Some thing went wrong", null))
                        }
                    }
                } catch (e: Exception) {
                    _verify_otp.postValue(Resource.error("${e.message}", null))
                }
            } else _verify_otp.postValue(Resource.error("No internet connection", null))
        }
    }

}