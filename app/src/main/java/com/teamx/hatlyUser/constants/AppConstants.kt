package com.teamx.hatlyUser.constants

import androidx.annotation.StringDef


object AppConstants {

    @StringDef(ApiConfiguration.BASE_URL)
    annotation class ApiConfiguration {
        companion object {
            const val BASE_URL = "http://192.168.100.33:8000/api/v1/"



//            const val BASE_URL = "https://multivendorbackend.herokuapp.com/api/"
        }
    }

    @StringDef(DbConfiguration.DB_NAME)
    annotation class DbConfiguration {
        companion object {
            const val DB_NAME = "BaseProject"
        }
    }


    @StringDef(
        DataStore.DATA_STORE_NAME,
        DataStore.LOCALIZATION_KEY_NAME,
        DataStore.USER_NAME_KEY,
        DataStore.TOKEN,
        DataStore.DEVICE_DATA,
        DataStore.SAVE_ID,
        DataStore.DETAILS,
        DataStore.AVATAR,
        DataStore.billId,
        DataStore.shipId,
        DataStore.NUMBER,
        DataStore.NAME
    )
    annotation class DataStore {
        companion object {
            const val DATA_STORE_NAME = "BaseProject"
            const val LOCALIZATION_KEY_NAME = "lang"
            const val USER_NAME_KEY = "user_name_key"
            const val TOKEN = "token"
            const val DEVICE_DATA = "deviceDATA"
            const val SAVE_ID = "save_id"
            const val DETAILS = "details"
            const val AVATAR = "avatar"
            const val NAME = "name"
            const val billId = "billId"
            const val shipId = "shipId"
            const val NUMBER = "number"
        }
    }

}