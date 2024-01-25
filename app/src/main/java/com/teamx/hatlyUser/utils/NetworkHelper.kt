package com.teamx.hatlyUser.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkHelper @Inject constructor(@ApplicationContext private val context: Context) {

//    fun isNetworkConnected(): Boolean {
//        var result = false
//        val connectivityManager =
//            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            val networkCapabilities = connectivityManager.activeNetwork ?: return false
//            val activeNetwork =
//                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
//            result = when {
//                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
//                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
//                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
//                else -> false
//            }
//
////            if (isInternetAvailable()){
////                Log.d("Internet", "Connected to WiFi and Internet is available")
////            }else{
////                Log.d("Internet", "Connected to WiFi but no Internet connection")
////            }
//
////            if (result) {
////                result = isInternetAvailable()
////            }
//        } else {
//            connectivityManager.run {
//                connectivityManager.activeNetworkInfo?.run {
//                    result = when (type) {
//                        ConnectivityManager.TYPE_WIFI -> true
//                        ConnectivityManager.TYPE_MOBILE -> true
//                        ConnectivityManager.TYPE_ETHERNET -> true
//                        else -> false
//                    }
//
//                }
//            }
//        }
//
//        return result
//    }




    suspend fun isNetworkConnected(): Boolean {
        return withContext(Dispatchers.IO) {
            if (isConnectedToWifi(context)) {
                try {
                    val request = Request.Builder()
                        .url("https://www.google.com")
                        .build()

                    val client = OkHttpClient()
                    val response = client.newCall(request).execute()

                    return@withContext response.isSuccessful
                } catch (e: IOException) {
                    return@withContext false
                }
            } else {
                return@withContext false
            }
        }
    }

    private fun isConnectedToWifi(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities =
                connectivityManager.getNetworkCapabilities(network)
            return capabilities != null &&
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_WIFI
        }
    }

}