package com.teamx.hatlyUser.ui.fragments.setting.contactus


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.home.model.FcmModel
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class ContactUsViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _contactUsResponse = MutableLiveData<Resource<FcmModel>>()
    val contactUsResponse: LiveData<Resource<FcmModel>>
        get() = _contactUsResponse

    fun contactUs(param: JsonObject) {
        viewModelScope.launch {
            _contactUsResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.contactUs(param).let {
                        if (it.isSuccessful) {
                            _contactUsResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _contactUsResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else if (it.code() == 401) {
                            _contactUsResponse.postValue(Resource.unAuth("", null))
                        }else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _contactUsResponse.postValue(Resource.error(jsonObj.getString("message")))
//                            _contactUsResponse.postValue(Resource.error(it.message(), null))
                        }
                    }
                } catch (e: Exception) {
                    _contactUsResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _contactUsResponse.postValue(Resource.error("No internet connection", null))
        }
    }

}