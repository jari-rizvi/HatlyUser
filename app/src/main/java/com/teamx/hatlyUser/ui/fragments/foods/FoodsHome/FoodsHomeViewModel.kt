package com.teamx.hatlyUser.ui.fragments.foods.FoodsHome


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.foods.FoodsHome.models.modelCategory.ModelFoodsCategory
import com.teamx.hatlyUser.ui.fragments.foods.FoodsHome.models.modelShops.ModelFoodShops
import com.teamx.hatlyUser.ui.fragments.hatlymart.stores.model.ModelAllStores
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class FoodsHomeViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _allFoodsCategories = MutableLiveData<Resource<ModelFoodsCategory>>()
    val allFoodsCategoriesResponse: LiveData<Resource<ModelFoodsCategory>>
        get() = _allFoodsCategories

    fun allFoodsCategories(page: Int, limit: Int) {
        viewModelScope.launch {
            _allFoodsCategories.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.allFoodsCategories(page, limit).let {
                        if (it.isSuccessful) {
                            _allFoodsCategories.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _allFoodsCategories.postValue(Resource.error(jsonObj.getString("message")))
                        }else if (it.code() == 401) {
                            _allFoodsCategories.postValue(Resource.unAuth("", null))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _allFoodsCategories.postValue(Resource.error(jsonObj.getString("message")))
                        }
                    }
                } catch (e: Exception) {
                    _allFoodsCategories.postValue(Resource.error("${e.message}", null))
                }
            } else _allFoodsCategories.postValue(Resource.error("No internet connection", null))
        }
    }

    private val _allFoodsShops = MutableLiveData<Resource<ModelFoodShops>>()
    val allFoodsShopsResponse: LiveData<Resource<ModelFoodShops>>
        get() = _allFoodsShops

    fun allFoodsShops(page: Int, limit: Int, offset: Int, search: String, id: String?) {
        viewModelScope.launch {
            _allFoodsShops.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.allFoodsShops(page, limit, offset, search, id).let {
                        if (it.isSuccessful) {
                            _allFoodsShops.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _allFoodsShops.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _allFoodsShops.postValue(Resource.error(jsonObj.getString("message")))
                        }
                    }
                } catch (e: Exception) {
                    _allFoodsShops.postValue(Resource.error("${e.message}", null))
                }
            } else _allFoodsShops.postValue(Resource.error("No internet connection", null))
        }
    }
}