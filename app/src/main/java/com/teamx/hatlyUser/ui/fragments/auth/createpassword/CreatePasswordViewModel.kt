package com.teamx.hatlyUser.ui.fragments.auth.createpassword


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.auth.createpassword.model.ModelUpdatePass
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


    private val _updatePassResponse = MutableLiveData<Resource<ModelUpdatePass>>()
    val updateResponse: LiveData<Resource<ModelUpdatePass>>
        get() = _updatePassResponse

    fun updatePass(param: JsonObject) {
        viewModelScope.launch {
            _updatePassResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.updatePass(param).let {
                        if (it.isSuccessful) {
                            _updatePassResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _updatePassResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _updatePassResponse.postValue(Resource.error("Some thing went wrong", null))
                        }
                    }
                } catch (e: Exception) {
                    _updatePassResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _updatePassResponse.postValue(Resource.error("No internet connection", null))
        }
    }

}