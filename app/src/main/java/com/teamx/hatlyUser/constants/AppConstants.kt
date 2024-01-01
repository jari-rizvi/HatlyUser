package com.teamx.hatlyUser.constants

import androidx.annotation.StringDef


object AppConstants {

    @StringDef(ApiConfiguration.BASE_URL)
    annotation class ApiConfiguration {
        companion object {

//            const val APP_URL = "http://192.168.100.49:8000/" /*sohail local*/
//            const val APP_URL = "http://192.168.100.79:8000/" /*tooba local*/
            const val APP_URL = "http://31.220.17.28:8000/" /*development*/

            const val BASE_URL = "${APP_URL}api/v1/"
//            const val BASE_URL = "http://192.168.100.79:8000/api/v1/" /*tooba local*/
//            const val BASE_URL = "http://31.220.17.28:8000/api/v1/" /*development*/
//            const val BASE_URL = "http://192.168.100.45:8000/api/v1/" /*farooq*/
//            const val BASE_URL = "https://2lcsrs26-8000.inc1.devtunnels.ms/api/v1/" /*saad*/







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