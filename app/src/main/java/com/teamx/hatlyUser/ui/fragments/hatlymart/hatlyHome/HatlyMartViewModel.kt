package com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.model.ModelHealthDetail
import com.teamx.hatlyUser.ui.fragments.hatlymart.stores.model.ModelAllStores
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class HatlyMartViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _healthDetail = MutableLiveData<Resource<ModelHealthDetail>>()
    val healthDetailResponse: LiveData<Resource<ModelHealthDetail>>
        get() = _healthDetail

    fun healthDeatil(id: String) {
        viewModelScope.launch {
            _healthDetail.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.healthDetail(id).let {
                        if (it.isSuccessful) {
                            _healthDetail.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _healthDetail.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _healthDetail.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d(
                                "allStoresResponse",
                                "onViewCreated: ${jsonObj.getString("message")}"
                            )
//                            _allStores.postValue(Resource.error(it.errorBody()!!.charStream().readText()))
//                            _allStores.postValue(Resource.error("Some thing went wrong", null))
                        }
                    }
                } catch (e: Exception) {
                    _healthDetail.postValue(Resource.error("${e.message}", null))
                }
            } else _healthDetail.postValue(Resource.error("No internet connection", null))
        }
    }

}