package com.teamx.hatlyUser.ui.fragments.hatlymart.hatlycategories


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.hatlymart.hatlyHome.model.categoryModel.ModelCategory
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class HatlyCategoriesViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _categoryShopResponse = MutableLiveData<Resource<ModelCategory>>()
    val categoryShopResponse: LiveData<Resource<ModelCategory>>
        get() = _categoryShopResponse

    fun categoryShop(shopId: String, page: Int, limit: Int, offset: Int) {
        viewModelScope.launch {
            _categoryShopResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.categoryShop(shopId,page,limit,offset).let {
                        if (it.isSuccessful) {
                            _categoryShopResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _categoryShopResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else if (it.code() == 401) {
                            _categoryShopResponse.postValue(Resource.unAuth("", null))
                        }else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _categoryShopResponse.postValue(Resource.error(jsonObj.getString("message")))
                        }
                    }
                } catch (e: Exception) {
                    _categoryShopResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _categoryShopResponse.postValue(Resource.error("No internet connection", null))
        }
    }

}