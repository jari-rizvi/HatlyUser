package com.teamx.hatlyUser.ui.fragments.auth.createpassword


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.auth.createpassword.model.ModelCreatePass
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class CreatePasswordViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {


    private val _createPassResponse = MutableLiveData<Resource<ModelCreatePass>>()
    val createResponse: LiveData<Resource<ModelCreatePass>>
        get() = _createPassResponse

    fun createPass(param: JsonObject) {
        viewModelScope.launch {
            _createPassResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.createPass(param).let {
                        if (it.isSuccessful) {
                            _createPassResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _createPassResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _createPassResponse.postValue(Resource.error("Some thing went wrong", null))
                        }
                    }
                } catch (e: Exception) {
                    _createPassResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _createPassResponse.postValue(Resource.error("No internet connection", null))
        }
    }

}