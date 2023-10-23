package com.teamx.hatlyUser.ui.fragments.wishlist


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.location.map.modelDefaultAddress.ModelDefaultAddress
import com.teamx.hatlyUser.ui.fragments.profile.orderdetail.modelReview.ModelReviewShop
import com.teamx.hatlyUser.ui.fragments.wishlist.modelWishList.ModelWishList
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.http.Query
import javax.inject.Inject


@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {


    private val _wishListResponse = MutableLiveData<Resource<ModelWishList>>()
    val wishListResponse: LiveData<Resource<ModelWishList>>
        get() = _wishListResponse

    fun wishList(limit: Int, page: Int) {
        viewModelScope.launch {
            _wishListResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.wishList(limit, page).let {
                        if (it.isSuccessful) {
                            _wishListResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _wishListResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d("uploadReviewImg", "jsonObj ${it.code()}: ${jsonObj.getString("message")}")
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _wishListResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d("uploadReviewImg", "jsonObj: ${jsonObj.getString("message")}")
                        }
                    }
                } catch (e: Exception) {
                    Log.d("uploadReviewImg", "Exception: ${e.message}")
                    _wishListResponse.postValue(Resource.error("${e.message}", null))
                }
            } else{
                _wishListResponse.postValue(Resource.error("No internet connection", null))
            }
        }
    }


    private val _favRemoveResponse = MutableLiveData<Resource<ModelDefaultAddress>>()
    val favRemoveResponse: LiveData<Resource<ModelDefaultAddress>>
        get() = _favRemoveResponse

    fun favRemove(params: JsonObject) {
        viewModelScope.launch {
            _favRemoveResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.favRemove(params).let {
                        if (it.isSuccessful) {
                            _favRemoveResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _favRemoveResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _favRemoveResponse.postValue(Resource.error(jsonObj.getString("message")))
                        }
                    }
                } catch (e: Exception) {
                    _favRemoveResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _favRemoveResponse.postValue(Resource.error("No internet connection", null))
        }
    }

}