package com.app.func.features.scheduler

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.app.func.MainActivity
import com.app.func.R
import com.app.func.notification.NotificationBuilder

class NotificationService : Service() {

    private val channelId = "daily_scheduler_notify"
    private var notificationBuilder: NotificationBuilder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        notificationBuilder = NotificationBuilder(applicationContext)
        val openIntent = Intent(this, MainActivity::class.java).apply {
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, openIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        notificationBuilder?.apply {
            val importance = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                NotificationManager.IMPORTANCE_DEFAULT
            } else {
                NotificationManagerCompat.IMPORTANCE_DEFAULT
            }
            createNotificationChannel(
                chanelId = channelId,
                channelName = "WorkManager Channel",
                channelDescription = "Channel for WorkManager notifications",
                importance = importance,
            )
            sendNotification(
                chanelId = channelId, smallIcon = R.mipmap.ic_launcher,
                contentTitle = "WorkManager Notification",
                contentText = "This is a notification from WorkManager",
                style = NotificationCompat.BigTextStyle()
                    .bigText("This is a notification from WorkManager"),
                notifyId = getNotificationId(),
                pendingIntent = pendingIntent,
                priority = NotificationCompat.PRIORITY_DEFAULT,
            )
        }
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}