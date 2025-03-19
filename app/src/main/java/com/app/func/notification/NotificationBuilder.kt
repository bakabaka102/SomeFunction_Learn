package com.app.func.notification

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.Style
import androidx.core.app.NotificationManagerCompat
import com.app.func.utils.Constants
import com.app.func.utils.Logger

class NotificationBuilder(val context: Context) : INotificationBuilder {

    override fun createNotificationChannel(
        chanelId: String,
        channelName: String,
        channelDescription: String,
        importance: Int,
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(chanelId, channelName, importance).apply {
                description = channelDescription
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel_name"
            val descriptionText = "channel_description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(Constants.CHANNEL_NOTIFY_ID, name, importance)
            //channel.apply { description = descriptionText }
            channel.description = descriptionText

            val channel2 =
                NotificationChannel(Constants.CHANNEL_NOTIFY_ID_2, "Channel2_name", importance)
            channel2.apply { description = "descriptionText" }
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
            notificationManager.createNotificationChannel(channel2)
        }
    }

    @SuppressLint("MissingPermission")
    override fun showNotification(
        chanelId: String,
        smallIcon: Int,
        contentTitle: String,
        contentText: String,
        style: Style,
        notifyId: Int,
        priority: Int,
    ) {
        val notification = NotificationCompat.Builder(context, chanelId)
            .setSmallIcon(smallIcon)
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setPriority(priority)
            .setStyle(style)
            .build()

        NotificationManagerCompat.from(context).apply {
            if (isPermitPostNotification()) {
                notify(notifyId, notification)
            } else {
                Logger.d("POST_NOTIFICATIONS is denied.")
            }
        }
    }

    private fun isPermitPostNotification(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }
}