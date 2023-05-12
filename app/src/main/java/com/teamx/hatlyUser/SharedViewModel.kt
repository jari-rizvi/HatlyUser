package com.teamx.hatlyUser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teamx.hatlyUser.baseclasses.BaseViewModel


/**
 * Shared View Model class for sharing data between fragments
 */
class SharedViewModel : BaseViewModel() {

    val clickOnContinueBtn: MutableLiveData<Boolean>? = null

}