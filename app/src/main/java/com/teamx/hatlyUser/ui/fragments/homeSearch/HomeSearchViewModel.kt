package com.teamx.hatlyUser.ui.fragments.homeSearch


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.auth.login.Model.ModelLogin
import com.teamx.hatlyUser.ui.fragments.home.model.FcmModel
import com.teamx.hatlyUser.ui.fragments.homeSearch.model.ModelHomeSearch
import com.teamx.hatlyUser.ui.fragments.location.map.modelDefaultAddress.ModelDefaultAddress
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.http.Query
import javax.inject.Inject


@HiltViewModel
class HomeSearchViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _homeSearchResponse = MutableLiveData<Resource<ModelHomeSearch>>()
    val homeSearchResponse: LiveData<Resource<ModelHomeSearch>>
        get() = _homeSearchResponse

    fun homeSearch(
        search: String,
        category: String,
        type: String,
        limit: Int,
        page: Int,
    ) {
        viewModelScope.launch {
            _homeSearchResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.homeSearch(search, category, type, limit, page).let {
                        if (it.isSuccessful) {
                            _homeSearchResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _homeSearchResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _homeSearchResponse.postValue(Resource.error(jsonObj.getString("message")))
//                            _homeSearchResponse.postValue(Resource.error(it.message(), null))
                        }
                    }
                } catch (e: Exception) {
                    _homeSearchResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _homeSearchResponse.postValue(Resource.error("No internet connection", null))
        }
    }
}