package com.teamx.hatlyUser.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.teamx.hatlyUser.R

class LocationPermission {

    companion object{
        fun requestPermission(context: Context):Boolean {
            return !(ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        }

//        fun contactPermission(context: Context):Boolean{
//            return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
//        }

        fun extractShortAddress(fullAddress: String?): String? {
            val addressParts = fullAddress?.split(", ")
            val shortAddress = addressParts?.get(0)
            return shortAddress
        }
    }


}