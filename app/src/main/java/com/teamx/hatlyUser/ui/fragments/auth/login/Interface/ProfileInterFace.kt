package com.teamx.hatlyUser.ui.fragments.auth.login.Interface

import com.teamx.hatlyUser.ui.fragments.auth.login.Model.ModelLogin
import com.teamx.hatlyUser.ui.fragments.auth.login.ModelGoogle.ModelWithGoogle

interface ProfileInterFace {
    fun profileData(modelLogin: ModelWithGoogle)
}