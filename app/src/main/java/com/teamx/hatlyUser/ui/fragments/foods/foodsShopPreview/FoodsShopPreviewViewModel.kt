package com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.foods.FoodsHome.models.modelCategory.ModelFoodsCategory
import com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.modelShopHome.FoodShopModel
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class FoodsShopPreviewViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _foodsShopHome = MutableLiveData<Resource<FoodShopModel>>()
    val foodsShopHomeResponse: LiveData<Resource<FoodShopModel>>
        get() = _foodsShopHome

    fun foodsShopHome(id:String) {
        viewModelScope.launch {
            _foodsShopHome.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.foodsShopHome(id).let {
                        if (it.isSuccessful) {
                            _foodsShopHome.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _foodsShopHome.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _foodsShopHome.postValue(Resource.error(jsonObj.getString("message")))
                        }
                    }
                } catch (e: Exception) {
                    _foodsShopHome.postValue(Resource.error("${e.message}", null))
                }
            } else _foodsShopHome.postValue(Resource.error("No internet connection", null))
        }
    }
}