package com.teamx.hatlyUser.baseclasses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


open class BaseViewModel : ViewModel() {


    private val _productId = MutableLiveData<String>()
    val productId: LiveData<String>
        get() = _productId

    fun setProductId(_productId: String) {
        this._productId.value = _productId
    }

    private val _vendorId = MutableLiveData<String>()
    val vendorId: LiveData<String>
        get() = _vendorId

    fun setVendorId(_vendorId: String) {
        this._vendorId.value = _vendorId
    }

    private val _vendorCategoryId = MutableLiveData<String>()
    val vendorCategoryId: LiveData<String>
        get() = _vendorCategoryId

    fun setVendorCategoryId(_vendorCategoryId: String) {
        this._vendorCategoryId.value = _vendorCategoryId
    }


}