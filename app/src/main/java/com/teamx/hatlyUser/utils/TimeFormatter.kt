package com.teamx.hatlyUser.utils

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.facebook.internal.Utility.locale
import com.teamx.hatlyUser.R
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class TimeFormatter {

    companion object {


        @RequiresApi(Build.VERSION_CODES.O)
        fun formatTimeDifference(timestamp: String, context: Context): String {
            val instant = Instant.parse(timestamp)
            val now = Instant.now()
            val duration = Duration.between(instant, now)

            return when {
                duration.toDays() > 2 -> {
                    // If more than 2 days, return the actual date
                    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
//                    localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

                    val defaultLocale = Locale.getDefault()
                    val languageCode = defaultLocale.language
                    val datePattern ="yyyy-MM-dd"
//                    datePattern = if (languageCode == "ar"){
//                        "dd-MM-yyyy"
//                    }else{
//                        "yyyy-MM-dd"
//                    }

                    Log.d("arabicNumberString", "arabicNumberString: ${languageCode}")

                    convertNumtoArabic(localDateTime.format(DateTimeFormatter.ofPattern(datePattern)), datePattern)
                }

                duration.toDays() > 1 -> "${convertNumtoArabic(duration.toDays())} days ago"
                duration.toDays() == 1L -> "1 day ago"
                duration.toHours() > 1 -> "${convertNumtoArabic(duration.toHours())} hours ago"
                duration.toHours() == 1L -> "1 hour ago"
                duration.toMinutes() > 1 -> "${convertNumtoArabic(duration.toMinutes())} minutes ago"
                duration.toMinutes() == 1L -> "1 minute ago"
                else -> context.getString(R.string.just_now)
            }
        }

        fun convertNumtoArabic(yourNumber: Long): String {
//            val yourNumber = 123456

            // Arabic locale
            val selectedLocale = Locale.getDefault()

            // Create a custom NumberFormat for Arabic
            val arabicNumberFormat = NumberFormat.getInstance(selectedLocale)
            arabicNumberFormat.maximumFractionDigits = 2 // Customize as needed

            // Format the number to Arabic font
            return arabicNumberFormat.format(yourNumber)
        }

        private fun convertNumtoArabic(inputDateString: String, datePattern: String): String {
// Assume user's selected locale, you can get it from the app settings or device settings
            val selectedLocale = Locale.getDefault()

            // Parse the input date string
            val inputFormat = SimpleDateFormat(datePattern, Locale.US)
            val date: Date = inputFormat.parse(inputDateString) ?: Date()

            // Create a date formatter with the selected locale
            val outputFormat = SimpleDateFormat(datePattern, selectedLocale)

            // Format the date to the desired locale
            return outputFormat.format(date)
        }

    }
}