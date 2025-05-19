package com.app.func.notification

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.Style
import androidx.core.app.NotificationManagerCompat
import com.app.func.utils.Logger
import java.util.Date

class NotificationBuilder(val context: Context) : INotificationBuilder {

    companion object {
        const val NOTIFICATION_CHANEL_ID = "notification_chanel"
    }

    override fun getNotificationId(): Int {
        //return System.currentTimeMillis().toInt()
        return Date().time.toInt().also {
            Logger.d("$it")
        }
    }

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
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("MissingPermission")
    override fun sendNotification(
        chanelId: String,
        smallIcon: Int,
        contentTitle: String,
        contentText: String,
        style: Style?,
        pendingIntent: PendingIntent?,
        notifyId: Int,
        priority: Int
    ) {
        val builder = NotificationCompat.Builder(context, chanelId)
            .setSmallIcon(smallIcon)
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setPriority(priority)
        val notification = builder.apply {
            setAutoCancel(true)
            setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
            if (style != null) builder.setStyle(style)
            if (pendingIntent != null) builder.setContentIntent(pendingIntent)
        }.build()
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