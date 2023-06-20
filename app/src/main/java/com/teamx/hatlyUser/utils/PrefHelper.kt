package com.teamx.hatlyUser.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class PrefHelper private constructor() {

    companion object {
        private val sharePref = PrefHelper()
        private lateinit var sharedPreferences: SharedPreferences

        private val IS_FIRST_TIME = "isFirstTime"

        private const val shippingConst = "_shippingAddress"

        fun getInstance(context: Context): PrefHelper {
            if (!::sharedPreferences.isInitialized) {
                synchronized(PrefHelper::class.java) {
                    if (!::sharedPreferences.isInitialized) {
                        sharedPreferences =
                            context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
                    }
                }
            }
            return sharePref
        }
    }

    val isNotFirstTime: Boolean get() = sharedPreferences.getBoolean(IS_FIRST_TIME, false)

    fun setNotFirstTime(order_id: Boolean) {
        sharedPreferences.edit().putBoolean(IS_FIRST_TIME, order_id).apply()

    }


    fun removePlaceObj() {
        sharedPreferences.edit().remove(shippingConst).apply()
    }

    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }

}
