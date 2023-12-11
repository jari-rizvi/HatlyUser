package com.teamx.hatlyUser.ui.fragments.auth.guest


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
class GuestViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _guestResponse = MutableLiveData<Resource<ModelLogin>>()
    val guestResponse: LiveData<Resource<ModelLogin>>
        get() = _guestResponse

    fun guest(param: JsonObject) {
        viewModelScope.launch {
            _guestResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.guest(param).let {
                        if (it.isSuccessful) {
                            _guestResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _guestResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else if (it.code() == 401) {
                            _guestResponse.postValue(Resource.unAuth("", null))
                        }else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _guestResponse.postValue(Resource.error(jsonObj.getString("message")))
//                            _guestResponse.postValue(Resource.error(it.message(), null))
                        }
                    }
                } catch (e: Exception) {
                    _guestResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _guestResponse.postValue(Resource.error("No internet connection", null))
        }
    }
}