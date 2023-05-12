package com.teamx.hatlyUser.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class PrefHelper private constructor() {

    companion object {
        private val sharePref = PrefHelper()
        private lateinit var sharedPreferences: SharedPreferences

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


    fun removePlaceObj() {
        sharedPreferences.edit().remove(shippingConst).apply()
    }

    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }

}
