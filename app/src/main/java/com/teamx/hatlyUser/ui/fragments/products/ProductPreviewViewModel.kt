package com.teamx.hatlyUser.ui.fragments.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.Resource
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.ui.fragments.foods.foodsShopPreview.modelShopHome.FoodShopModel
import com.teamx.hatlyUser.ui.fragments.products.model.ModelProductPreview
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class ProductPreviewViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {


    private val _prodPreviewHome = MutableLiveData<Resource<ModelProductPreview>>()
    val prodPreviewResponse: LiveData<Resource<ModelProductPreview>>
        get() = _prodPreviewHome

    fun prodPreview(id:String) {
        viewModelScope.launch {
            _prodPreviewHome.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.prodPreview(id).let {
                        if (it.isSuccessful) {
                            _prodPreviewHome.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _prodPreviewHome.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _prodPreviewHome.postValue(Resource.error(jsonObj.getString("message")))
                        }
                    }
                } catch (e: Exception) {
                    _prodPreviewHome.postValue(Resource.error("${e.message}", null))
                }
            } else _prodPreviewHome.postValue(Resource.error("No internet connection", null))
        }
    }

}