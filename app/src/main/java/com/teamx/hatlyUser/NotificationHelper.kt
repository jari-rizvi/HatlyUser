package com.teamx.hatlyUser

import android.content.Context
import android.util.Log
import com.teamx.hatlyUser.ui.activity.mainActivity.MainActivity.Companion.service
import org.json.JSONException
import org.json.JSONObject

object NotificationHelper {


    fun displayNotification(
        title: String?,
        description: String
    ) {
        service!!.showNotification1(title!!, description)
    }

    fun displayNotification(
        title: String?,
        description: String,
        pushNotificationModel: PushNotificationModel
    ) {
        service!!.showNotification1(title!!, description, pushNotificationModel)

    }
}