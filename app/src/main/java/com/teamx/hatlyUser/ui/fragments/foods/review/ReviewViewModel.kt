package com.teamx.hatlyUser.ui.fragments.foods.review


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.foods.review.modelReviewList.ModelReviewList
import com.teamx.hatlyUser.ui.fragments.profile.orderdetail.modelUploadImages.ModelUploadImages
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _reviewListResponse = MutableLiveData<Resource<ModelReviewList>>()
    val reviewListResponse: LiveData<Resource<ModelReviewList>>
        get() = _reviewListResponse

    fun reviewList(id: String, limit: Int, page: Int) {
        viewModelScope.launch {
            _reviewListResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.reviewList(id, limit, page).let {
                        if (it.isSuccessful) {
                            _reviewListResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _reviewListResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d(
                                "uploadReviewImg",
                                "jsonObj ${it.code()}: ${jsonObj.getString("message")}"
                            )
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _reviewListResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d("uploadReviewImg", "jsonObj: ${jsonObj.getString("message")}")
                        }
                    }
                } catch (e: Exception) {
                    Log.d("uploadReviewImg", "Exception: ${e.message}")
                    _reviewListResponse.postValue(Resource.error("${e.message}", null))
                }
            } else {
                _reviewListResponse.postValue(Resource.error("No internet connection", null))
            }
        }
    }
}