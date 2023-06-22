package com.teamx.hatlyUser.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.teamx.hatlyUser.constants.AppConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreProvider(var context: Context) {

    //Create the dataStore

    //Create some keys
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = AppConstants.DataStore.DATA_STORE_NAME)
        val IS_LOCALIZATION_KEY =
            booleanPreferencesKey(AppConstants.DataStore.LOCALIZATION_KEY_NAME)
        val USER_NAME_KEY = stringPreferencesKey(AppConstants.DataStore.USER_NAME_KEY)
        val TOKEN = stringPreferencesKey(AppConstants.DataStore.TOKEN)
        val DEVICE_DATA = stringPreferencesKey(AppConstants.DataStore.DEVICE_DATA)
        val SAVE_ID = stringPreferencesKey(AppConstants.DataStore.SAVE_ID)
        val DETAILS = stringPreferencesKey(AppConstants.DataStore.DETAILS)
        val AVATAR = stringPreferencesKey(AppConstants.DataStore.AVATAR)
        val NUMBER = stringPreferencesKey(AppConstants.DataStore.NUMBER)
        val NAME = stringPreferencesKey(AppConstants.DataStore.NAME)
        val billId = stringPreferencesKey(AppConstants.DataStore.billId)
        val shipId = stringPreferencesKey(AppConstants.DataStore.shipId)
    }

    //Store data
    suspend fun storeData(
        isLocalizationKey: Boolean,
        name: String,
        token: String,
        details: String
    ) {
        context.dataStore.edit {
            it[IS_LOCALIZATION_KEY] = isLocalizationKey
            it[USER_NAME_KEY] = name
            it[TOKEN] = token
            it[DETAILS] = details
        }

    }


    //get Token by using this
    val token: Flow<String?> = context.dataStore.data.map {
        it[TOKEN]
    }

    val deviceDATA: Flow<String?> = context.dataStore.data.map {
        it[DEVICE_DATA]
    }
    val billId1: Flow<String?> = context.dataStore.data.map {
        it[billId]
    }
    val shipId1: Flow<String?> = context.dataStore.data.map {
        it[shipId]
    }

    val details: Flow<String?> = context.dataStore.data.map {
        it[DETAILS]
    }
    val avatar: Flow<String?> = context.dataStore.data.map {
        it[AVATAR]
    }

    val name: Flow<String?> = context.dataStore.data.map {
        it[NAME]
    }
        val saveId: Flow<String?> = context.dataStore.data.map {
            it[SAVE_ID]
        }
    val number: Flow<String?> = context.dataStore.data.map {
        it[NUMBER]
    }

    //save token by using this functionn
    suspend fun saveUserToken(token: String) {
        context.dataStore.edit {
            it[TOKEN] = token
        }
    }

    suspend fun saveDeviceData(deviceData: String) {
        context.dataStore.edit {
            it[DEVICE_DATA] = deviceData
        }
    }

    //save id by using this functionn
    suspend fun saveUserID(SAVE_ID1: String) {
        context.dataStore.edit {
            it[SAVE_ID] = SAVE_ID1
        }
    }

    //save token by using this functionn
    suspend fun removeAll() {
        context.dataStore.edit {
            it.remove(TOKEN)
            it.remove(DEVICE_DATA)
            it.remove(DETAILS)
            it.remove(SAVE_ID)
            it.remove(AVATAR)
            it.remove(NAME)
            it.remove(NUMBER)

        }
    }

    suspend fun saveUserDetails(firstname: String, email: String) {
        context.dataStore.edit {
            it[DETAILS] = firstname
            it[DETAILS] = email
        }
    }

    suspend fun saveAddressIds(billId2: String, shipId2: String) {
        context.dataStore.edit {
            it[billId] = billId2
            it[shipId] = shipId2
        }
    }

    suspend fun saveUserDetails(firstname: String, email: String, avatar: String, number: String) {
        context.dataStore.edit {
            it[NAME] = firstname
            it[DETAILS] = email
            it[AVATAR] = avatar
            it[NUMBER] = number
        }
    }


    //Create an Localization flow
    val localizationFlow: Flow<Boolean> = context.dataStore.data.map {
        it[IS_LOCALIZATION_KEY] ?: false
    }

}