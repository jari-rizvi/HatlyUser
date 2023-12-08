package com.teamx.hatlyUser.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.teamx.hatlyUser.dataclasses.ShippingAddress
import com.teamx.hatlyUser.ui.fragments.auth.login.Model.ModelLogin

class PrefHelper private constructor() {

    companion object {
        private val sharePref = PrefHelper()
        private lateinit var sharedPreferences: SharedPreferences

        private val IS_FIRST_TIME = "isFirstTime"

        private const val shippingConst = "_shippingAddress"
        private const val USER_DATA = "USERDATA"
        private val LANGTYPE = "lang_type"

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


    fun saveWord(word: String) {
        if (word.isEmpty()){
            return
        }
        // Save the word in SharedPreferences
        val editor = sharedPreferences.edit()
        // Retrieve the existing list of words
        val currentWords = loadSavedWords().toMutableList()

        // Ensure only the latest 'maxWords' words are kept
        if (currentWords.size >= 5) {
            currentWords.removeAt(currentWords.size - 1) // Remove the last (oldest) word
        }

        // Add the new word to the beginning of the list
        currentWords.add(0, word)

        // Save the updated list of words
        editor.putString("savedWords", currentWords.joinToString(","))
        editor.apply()
    }

    fun loadSavedWords(): ArrayList<String> {
        // Load the saved words from SharedPreferences
        val wordsString = sharedPreferences.getString("savedWords", "") ?: ""
        return ArrayList(wordsString.split(",").filter { it.isNotBlank() })
    }

    fun setNotFirstTime(order_id: Boolean) {
        sharedPreferences.edit().putBoolean(IS_FIRST_TIME, order_id).apply()

    }

    val langType: String?
        get() = sharedPreferences.getString(LANGTYPE, "en")


    fun saveLANGTYPE(lang_type: String) {
        sharedPreferences.edit().putString(LANGTYPE, lang_type).apply()
    }


    fun removePlaceObj() {
        sharedPreferences.edit().remove(shippingConst).apply()
    }

    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }

    fun getUserData(): ModelLogin? {
        val gson = Gson()
        return gson.fromJson(
            sharedPreferences.getString(USER_DATA, ""), ModelLogin::class.java
        )
    }

    fun setUserData(shippingAddress: ModelLogin?) {
        val gson = Gson()
        val str = gson.toJson(shippingAddress)
        sharedPreferences.edit().putString(USER_DATA, str).apply()
    }

}
