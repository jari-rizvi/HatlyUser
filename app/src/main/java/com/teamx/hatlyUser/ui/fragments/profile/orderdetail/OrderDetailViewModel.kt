package com.teamx.hatlyUser.ui.fragments.profile.orderdetail


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.auth.forgotpassword.model.ModelForgotPass
import com.teamx.hatlyUser.ui.fragments.profile.orderdetail.modelReview.ModelReviewShop
import com.teamx.hatlyUser.ui.fragments.profile.orderdetail.modelUploadImages.ModelUploadImages
import com.teamx.hatlyUser.ui.fragments.profile.orderhistory.model.Doc
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

    private val _orderDetailResponse = MutableLiveData<Resource<Doc>>()
    val orderDetailResponse: LiveData<Resource<Doc>>
        get() = _orderDetailResponse

    fun orderDetail(id: String) {
        viewModelScope.launch {
            _orderDetailResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.orderDetail(id).let {
                        if (it.isSuccessful) {
                            _orderDetailResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _orderDetailResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d(
                                "uploadReviewImg",
                                "jsonObj ${it.code()}: ${jsonObj.getString("message")}"
                            )
                        } else if (it.code() == 401) {
                            _orderDetailResponse.postValue(Resource.unAuth("", null))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _orderDetailResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d("uploadReviewImg", "jsonObj: ${jsonObj.getString("message")}")
                        }
                    }
                } catch (e: Exception) {
                    Log.d("uploadReviewImg", "Exception: ${e.message}")
                    _orderDetailResponse.postValue(Resource.error("${e.message}", null))
                }
            } else {
                _orderDetailResponse.postValue(Resource.error("No internet connection", null))
            }
        }
    }

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
                            Log.d(
                                "uploadReviewImg",
                                "jsonObj ${it.code()}: ${jsonObj.getString("message")}"
                            )
                        } else if (it.code() == 401) {
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
            } else {
                _uploadReviewImgResponse.postValue(Resource.error("No internet connection", null))
            }
        }
    }


    private val _reviewOrderResponse = MutableLiveData<Resource<ModelReviewShop>>()
    val reviewOrderResponse: LiveData<Resource<ModelReviewShop>>
        get() = _reviewOrderResponse

    fun reviewOrder(params: JsonObject) {
        viewModelScope.launch {
            _reviewOrderResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.reviewOrder(params).let {
                        if (it.isSuccessful) {
                            _reviewOrderResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _reviewOrderResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d(
                                "uploadReviewImg",
                                "jsonObj ${it.code()}: ${jsonObj.getString("message")}"
                            )
                        } else if (it.code() == 401) {
                            _reviewOrderResponse.postValue(Resource.unAuth("", null))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _reviewOrderResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d("uploadReviewImg", "jsonObj: ${jsonObj.getString("message")}")
                        }
                    }
                } catch (e: Exception) {
                    Log.d("uploadReviewImg", "Exception: ${e.message}")
                    _reviewOrderResponse.postValue(Resource.error("${e.message}", null))
                }
            } else {
                _reviewOrderResponse.postValue(Resource.error("No internet connection", null))
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
                        } else if (it.code() == 401) {
                            _reOrderResponse.postValue(Resource.unAuth("", null))
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


    private val _cancelOrderResponse = MutableLiveData<Resource<ModelForgotPass>>()
    val cancelOrderResponse: LiveData<Resource<ModelForgotPass>>
        get() = _cancelOrderResponse

    fun cancelOrder(id: String) {
        viewModelScope.launch {
            _cancelOrderResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.cancelOrder(id).let {
                        if (it.isSuccessful) {
                            _cancelOrderResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _cancelOrderResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else if (it.code() == 401) {
                            _cancelOrderResponse.postValue(Resource.unAuth("", null))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _cancelOrderResponse.postValue(Resource.error(jsonObj.getString("message")))
                        }
                    }
                } catch (e: Exception) {
                    _cancelOrderResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _cancelOrderResponse.postValue(Resource.error("No internet connection", null))
        }
    }

}