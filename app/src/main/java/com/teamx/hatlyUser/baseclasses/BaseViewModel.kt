package com.teamx.hatlyUser.baseclasses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teamx.hatlyUser.ui.fragments.auth.login.Model.ModelLogin


open class BaseViewModel : ViewModel() {


    private val _userShared = MutableLiveData<ModelLogin>()
    val userData: LiveData<ModelLogin>
        get() = _userShared

    fun setUserData(_userId: ModelLogin) {
        this._userShared.value = _userId
    }




}