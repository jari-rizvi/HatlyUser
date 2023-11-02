package com.teamx.hatlyUser.baseclasses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teamx.hatlyUser.ui.fragments.auth.login.Model.Location
import com.teamx.hatlyUser.ui.fragments.auth.login.Model.ModelLogin
import com.teamx.hatlyUser.ui.fragments.profile.orderhistory.model.Doc


open class BaseViewModel : ViewModel() {


    private val _userShared = MutableLiveData<ModelLogin>()
    val userData: LiveData<ModelLogin>
        get() = _userShared

    fun setUserData(_userId: ModelLogin) {
        this._userShared.value = _userId
    }


    private val _orderhistory = MutableLiveData<Doc>()
    val orderHistory: LiveData<Doc>
        get() = _orderhistory

    fun setOrderHistory(doc: Doc) {
        this._orderhistory.value = doc
    }


    private val _locationmodel = MutableLiveData<Location>()
    val locationmodel: LiveData<Location>
        get() = _locationmodel

    fun setlocationmodel(createAddressModelItem: Location?) {
        this._locationmodel.value = createAddressModelItem
    }


    private val _parcelLocation = MutableLiveData<Location>()
    val parcelLocation: LiveData<Location>
        get() = _parcelLocation

    fun setParcelLocation(createAddressModelItem: Location?) {
        this._parcelLocation.value = createAddressModelItem
    }



}