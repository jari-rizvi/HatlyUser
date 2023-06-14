package com.teamx.hatlyUser.ui.fragments.setting.helpsupport


import com.teamx.hatlyUser.baseclasses.BaseViewModel
import com.teamx.hatlyUser.data.remote.reporitory.MainRepository
import com.teamx.hatlyUser.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HelpSupportViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

}