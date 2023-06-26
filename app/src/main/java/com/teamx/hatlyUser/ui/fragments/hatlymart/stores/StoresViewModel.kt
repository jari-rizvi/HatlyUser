package com.teamx.hatlyUser.ui.fragments.hatlymart.stores


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.auth.login.Model.ModelLogin
import com.teamx.hatlyUser.ui.fragments.hatlymart.stores.model.ModelAllStores
import com.teamx.hatlyUser.ui.fragments.hatlymart.stores.model.ModelAllStoresItem
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class StoresViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _allStores = MutableLiveData<Resource<ModelAllStores>>()
    val allStoresResponse: LiveData<Resource<ModelAllStores>>
        get() = _allStores

    fun allStores(page: Int, limit: Int, search: String) {
        viewModelScope.launch {
            _allStores.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.allStores(page, limit, search).let {
                        if (it.isSuccessful) {
                            _allStores.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _allStores.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _allStores.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d(
                                "allStoresResponse",
                                "onViewCreated: ${jsonObj.getString("message")}"
                            )
//                            _allStores.postValue(Resource.error(it.errorBody()!!.charStream().readText()))
//                            _allStores.postValue(Resource.error("Some thing went wrong", null))
                        }
                    }
                } catch (e: Exception) {
                    _allStores.postValue(Resource.error("${e.message}", null))
                }
            } else _allStores.postValue(Resource.error("No internet connection", null))
        }
    }

}