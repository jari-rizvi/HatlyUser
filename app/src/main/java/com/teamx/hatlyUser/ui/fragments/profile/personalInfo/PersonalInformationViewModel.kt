package com.teamx.hatlyUser.ui.fragments.profile.personalInfo


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.auth.forgotpassword.model.ModelForgotPass
import com.teamx.hatlyUser.ui.fragments.auth.login.Model.ModelLogin
import com.teamx.hatlyUser.ui.fragments.profile.orderdetail.modelUploadImages.ModelUploadImages
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class PersonalInformationViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _uploadReviewImgResponse = MutableLiveData<Resource<ModelUploadImages>>()
    val uploadReviewImgResponse: LiveData<Resource<ModelUploadImages>>
        get() = _uploadReviewImgResponse

    fun uploadReviewImg(imageParts: List<MultipartBody.Part>) {
        viewModelScope.launch {
            _uploadReviewImgResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.uploadReviewImg(imageParts).let {
                        if (it.isSuccessful) {
                            _uploadReviewImgResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _uploadReviewImgResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d("uploadReviewImg", "jsonObj ${it.code()}: ${jsonObj.getString("message")}")
                        }else if (it.code() == 401) {
                            _uploadReviewImgResponse.postValue(Resource.unAuth("", null))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _uploadReviewImgResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d("uploadReviewImg", "jsonObj: ${jsonObj.getString("message")}")
                        }
                    }
                } catch (e: Exception) {
                    Log.d("uploadReviewImg", "Exception: ${e.message}")
                    _uploadReviewImgResponse.postValue(Resource.error("${e.message}", null))
                }
            } else{
                _uploadReviewImgResponse.postValue(Resource.error("No internet connection", null))
            }
        }
    }

    private val _updateProfileResponse = MutableLiveData<Resource<ModelLogin>>()
    val updateProfileResponse: LiveData<Resource<ModelLogin>>
        get() = _updateProfileResponse

    fun updateProfile(params: JsonObject,) {
        viewModelScope.launch {
            _updateProfileResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.updateProfile(params).let {
                        if (it.isSuccessful) {
                            _updateProfileResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _updateProfileResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d("uploadReviewImg", "jsonObj ${it.code()}: ${jsonObj.getString("message")}")
                        }else if (it.code() == 401) {
                            _updateProfileResponse.postValue(Resource.unAuth("", null))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _updateProfileResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d("uploadReviewImg", "jsonObj: ${jsonObj.getString("message")}")
                        }
                    }
                } catch (e: Exception) {
                    Log.d("uploadReviewImg", "Exception: ${e.message}")
                    _updateProfileResponse.postValue(Resource.error("${e.message}", null))
                }
            } else{
                _updateProfileResponse.postValue(Resource.error("No internet connection", null))
            }
        }
    }

    private val _sendOtpProfileResponse = MutableLiveData<Resource<ModelForgotPass>>()
    val sendOtpProfileResponse: LiveData<Resource<ModelForgotPass>>
        get() = _sendOtpProfileResponse

    fun sendOtpProfile(params: JsonObject,) {
        viewModelScope.launch {
            _sendOtpProfileResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.sendOtpProfile(params).let {
                        if (it.isSuccessful) {
                            _sendOtpProfileResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _sendOtpProfileResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d("uploadReviewImg", "jsonObj ${it.code()}: ${jsonObj.getString("message")}")
                        }else if (it.code() == 401) {
                            _sendOtpProfileResponse.postValue(Resource.unAuth("", null))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _sendOtpProfileResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d("uploadReviewImg", "jsonObj: ${jsonObj.getString("message")}")
                        }
                    }
                } catch (e: Exception) {
                    Log.d("uploadReviewImg", "Exception: ${e.message}")
                    _sendOtpProfileResponse.postValue(Resource.error("${e.message}", null))
                }
            } else{
                _sendOtpProfileResponse.postValue(Resource.error("No internet connection", null))
            }
        }
    }

    private val _verifyOtpProfileResponse = MutableLiveData<Resource<ModelForgotPass>>()
    val verifyOtpProfileResponse: LiveData<Resource<ModelForgotPass>>
        get() = _verifyOtpProfileResponse

    fun verifyOtpProfile(params: JsonObject,) {
        viewModelScope.launch {
            _verifyOtpProfileResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.verifyOtpProfile(params).let {
                        if (it.isSuccessful) {
                            _verifyOtpProfileResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _verifyOtpProfileResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d("uploadReviewImg", "jsonObj ${it.code()}: ${jsonObj.getString("message")}")
                        }else if (it.code() == 401) {
                            _verifyOtpProfileResponse.postValue(Resource.unAuth("", null))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _verifyOtpProfileResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d("uploadReviewImg", "jsonObj: ${jsonObj.getString("message")}")
                        }
                    }
                } catch (e: Exception) {
                    Log.d("uploadReviewImg", "Exception: ${e.message}")
                    _verifyOtpProfileResponse.postValue(Resource.error("${e.message}", null))
                }
            } else{
                _verifyOtpProfileResponse.postValue(Resource.error("No internet connection", null))
            }
        }
    }

}