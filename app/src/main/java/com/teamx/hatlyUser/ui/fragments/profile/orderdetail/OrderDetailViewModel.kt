package com.teamx.hatlyUser.ui.fragments.profile.orderdetail


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.auth.forgotpassword.model.ModelForgotPass
import com.teamx.hatlyUser.ui.fragments.profile.orderdetail.modelUploadImages.ModelUploadImages
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class OrderDetailViewModel @Inject constructor(
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



    private val _reOrderResponse = MutableLiveData<Resource<ModelForgotPass>>()
    val reOrderResponse: LiveData<Resource<ModelForgotPass>>
        get() = _reOrderResponse

    fun reOrder(id: String) {
        viewModelScope.launch {
            _reOrderResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.reOrder(id).let {
                        if (it.isSuccessful) {
                            _reOrderResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _reOrderResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _reOrderResponse.postValue(Resource.error(jsonObj.getString("message")))
                        }
                    }
                } catch (e: Exception) {
                    _reOrderResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _reOrderResponse.postValue(Resource.error("No internet connection", null))
        }
    }

}