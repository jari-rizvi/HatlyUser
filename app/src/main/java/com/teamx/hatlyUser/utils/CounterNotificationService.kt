package com.teamx.hatlyUser.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.teamx.hatlyUser.PushNotificationModel
import com.teamx.hatlyUser.PushNotificationService
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.ui.activity.mainActivity.MainActivity


class CounterNotificationService(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    fun showNotification1(title: String, description: String, pushNotificationModel1: PushNotificationModel) {
        // Step 1: Create Notification Channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Step 1: Create Notification Channel
            val channel = NotificationChannel(
                COUNTER_CHANNEL_ID,
                "Channel Name",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            // Create the channel
            notificationManager.createNotificationChannel(channel)
        }

// Step 2: Create an Intent for the MainActivity
        val activityIntent = Intent(context, MainActivity::class.java)

        pushNotificationModel1.let {
            Log.d("order_Idorder_Id", "showNotification1: ${it}")
            activityIntent.putExtra("order_id", it.orderId)
            activityIntent.putExtra("order_status", it.status)
        }

        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

// Step 3: Create an Intent for the BroadcastReceiver
        val incrementIntent = PendingIntent.getBroadcast(
            context,
            2,
            Intent(context, PushNotificationService::class.java),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )

        val notificationId = System.currentTimeMillis().toInt()
// Step 4: Build and show the Notification
        val notificationBuilder = NotificationCompat.Builder(context, COUNTER_CHANNEL_ID)
            .setSmallIcon(R.drawable.hatly_logo_svg)
            .setContentTitle(title)
            .setContentText(description)
            .setContentIntent(activityPendingIntent)
            .addAction(
                R.drawable.hatly_logo_svg,
                "View",
                incrementIntent
            )

// Set channel only if it is not null (i.e., for Oreo and higher)


        val notification = notificationBuilder.build()

// Step 5: Notify with a unique ID
        notificationManager.notify(notificationId, notification)

    }

    fun showNotification1(title: String, description: String) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Step 1: Create Notification Channel
            val channel = NotificationChannel(
                COUNTER_CHANNEL_ID,
                "Channel Name",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            // Create the channel
            notificationManager.createNotificationChannel(channel)
        }

// Step 2: Create an Intent for the MainActivity
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

// Step 3: Create an Intent for the BroadcastReceiver
        val incrementIntent = PendingIntent.getBroadcast(
            context,
            2,
            Intent(context, PushNotificationService::class.java),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )

        val notificationId = System.currentTimeMillis().toInt()
// Step 4: Build and show the Notification
        val notificationBuilder = NotificationCompat.Builder(context, COUNTER_CHANNEL_ID)
            .setSmallIcon(R.drawable.hatly_logo_svg)
            .setContentTitle(title)
            .setContentText(description)
            .setContentIntent(activityPendingIntent)
            .addAction(
                R.drawable.hatly_logo_svg,
                "View",
                incrementIntent
            )

// Set channel only if it is not null (i.e., for Oreo and higher)


        val notification = notificationBuilder.build()

// Step 5: Notify with a unique ID
        notificationManager.notify(notificationId, notification)

    }
    companion object {
        const val COUNTER_CHANNEL_ID = "counter_channel"
    }
}