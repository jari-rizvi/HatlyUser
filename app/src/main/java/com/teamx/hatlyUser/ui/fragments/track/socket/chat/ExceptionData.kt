package com.teamx.hatlyUser.ui.fragments.track.socket.chat
import androidx.annotation.Keep

@Keep
data class ExceptionData(
    val message: Any, val type: String
)