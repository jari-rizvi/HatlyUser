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
import com.teamx.hatlyUser.PushNotificationService
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.ui.activity.mainActivity.MainActivity


class CounterNotificationService(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

//    fun showNotification(counter: Int) {
//        val activityIntent = Intent(context, MainActivity::class.java)
//        val activityPendingIntent = PendingIntent.getActivity(
//            context,
//            1,
//            activityIntent,
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
//        )
//        val incrementIntent = PendingIntent.getBroadcast(
//            context,
//            2,
//            Intent(context, CounterNotificationReceiver::class.java),
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
//        )
//        val notification = NotificationCompat.Builder(context, COUNTER_CHANNEL_ID)
//            .setSmallIcon(R.drawable.hatly_logo_svg)
//            .setContentTitle("Increment counter")
//            .setContentText("The count is $counter")
//            .setContentIntent(activityPendingIntent)
//            .addAction(
//                R.drawable.hatly_logo_svg,
//                "Increment",
//                incrementIntent
//            )
//            .build()
//
//        notificationManager.notify(1, notification)
//    }

//    fun showNotification1(header: String, comment: String) {
//        val activityIntent = Intent(context, MainActivity::class.java)
//        val activityPendingIntent = PendingIntent.getActivity(
//            context,
//            1,
//            activityIntent,
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
//        )
//         val incrementIntent = PendingIntent.getBroadcast(
//             context,
//             2,
//             Intent(context, PushNotificationService::class.java),
//             if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
//         )
////        Log.d("showNotification1", "showNotification1464: ${comment}")
//        val notification = NotificationCompat.Builder(context, COUNTER_CHANNEL_ID)
//            .setSmallIcon(R.drawable.hatly_logo_svg)
//            .setContentTitle(header)
//            .setContentText(comment)
//            .setContentIntent(activityPendingIntent)
//            .addAction(
//                R.drawable.hatly_logo_svg,
//                "View",
//                incrementIntent
//            ).build()
//
//        notificationManager.notify(1, notification)
//    }





//    fun showNotification(header: String, comment: String) {
//        // Create an intent to open an activity when the notification is clicked
//        val intent = Intent(context, MainActivity::class.java)
//
//        val pendingIntent = PendingIntent.getActivity(context, 0, intent,
//            PendingIntent.FLAG_IMMUTABLE)
//
//        // Create a notification
//        val notification = NotificationCompat.Builder(context, "my_channel_id")
//            .setSmallIcon(R.drawable.hatly_logo_svg)
//            .setContentTitle(header)
//            .setContentText(comment)
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .setContentIntent(pendingIntent)
//            .setAutoCancel(true)
//            .build()
//
//        // Display the notification
//        val notificationManager1 = NotificationManagerCompat.from(context)
//        if (ActivityCompat.checkSelfPermission(
//                context,
//                Manifest.permission.POST_NOTIFICATIONS
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return
//        }
//        Log.d("showNotification1", "description: PERMISSION_GRANTED")
//        notificationManager1.notify(1, notification) // You can use different IDs for different notifications
//    }



    fun showNotification1(title: String, description: String) {


        // Step 1: Create Notification Channel

        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                COUNTER_CHANNEL_ID,
                "Channel Name",
                NotificationManager.IMPORTANCE_DEFAULT
            )
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        notificationManager.createNotificationChannel(channel)

        // Step 2: Create an Intent for the MainActivity
        val activityIntent = Intent(context, MainActivity::class.java)
//        activityIntent.putExtra("typeDir", type)
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
        val notification = NotificationCompat.Builder(context, COUNTER_CHANNEL_ID)
            .setSmallIcon(R.drawable.hatly_logo_svg)
            .setContentTitle(title)
            .setContentText(description)
            .setContentIntent(activityPendingIntent)
            .addAction(
                R.drawable.hatly_logo_svg,
                "View",
                incrementIntent
            )
            .build()

        // Step 5: Notify with a unique ID
        notificationManager.notify(notificationId, notification)
    }


//    fun showNotification1(title: String, description: String, type: String, orderID: String) {
//
//
//        // Step 1: Create Notification Channel
//
//        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel(
//                COUNTER_CHANNEL_ID,
//                "Channel Name",
//                NotificationManager.IMPORTANCE_DEFAULT
//            )
//        } else {
//            null
//        }
//        if (channel != null) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                notificationManager.createNotificationChannel(channel)
//            }
//        }
//
//        // Step 2: Create an Intent for the MainActivity
//        val activityIntent = Intent(context, MainActivity::class.java)
//        activityIntent.putExtra("typeDir", type)
//        val activityPendingIntent = PendingIntent.getActivity(
//            context,
//            1,
//            activityIntent,
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )
//
//        // Step 3: Create an Intent for the BroadcastReceiver
//        val incrementIntent = PendingIntent.getBroadcast(
//            context,
//            2,
//            Intent(context, PushNotificationService::class.java),
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
//        )
//
//        val notificationId = System.currentTimeMillis().toInt()
//        // Step 4: Build and show the Notification
//        val notification = NotificationCompat.Builder(context, COUNTER_CHANNEL_ID)
//            .setSmallIcon(R.drawable.hatly_logo_svg)
//            .setContentTitle(title)
//            .setContentText(description)
//            .setContentIntent(activityPendingIntent)
//            .addAction(
//                R.drawable.hatly_logo_svg,
//                "View",
//                incrementIntent
//            )
//            .build()
//
//        // Step 5: Notify with a unique ID
//        notificationManager.notify(notificationId, notification)
//    }


    fun showNotification1(title: String, description: String, type: String, orderID: String) {


//        val notificationManager = NotificationManagerCompat.from(context)

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




//    fun showNotification1(header: String, comment: String, type: String, orderID: String) {
//        val activityIntent = Intent(context, MainActivity::class.java)
//
//        activityIntent.putExtra("typeDir", type)
//
//        val activityPendingIntent = PendingIntent.getActivity(
//            context,
//            1,
//            activityIntent,
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )
//
//        val incrementIntent = PendingIntent.getBroadcast(
//            context,
//            2,
//            Intent(context, PushNotificationService::class.java),
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
//        )
//        Log.d("showNotification1", "showNotification122 : ${comment}")
//        val notification = NotificationCompat.Builder(context, COUNTER_CHANNEL_ID)
//            .setSmallIcon(R.drawable.hatly_logo_svg)
//            .setContentTitle(header)
//            .setContentText(comment)
//            .setContentIntent(activityPendingIntent)
//            .addAction(
//                R.drawable.hatly_logo_svg,
//                "View",
//                incrementIntent
//            )
//            .build()
//
//        notificationManager.notify(1, notification)
//    }


    companion object {
        const val COUNTER_CHANNEL_ID = "counter_channel"
    }
}