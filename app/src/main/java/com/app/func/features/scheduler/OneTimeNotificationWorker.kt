package com.app.func.features.scheduler

import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.app.func.R
import com.app.func.notification.NotificationBuilder

class OneTimeNotificationWorker(
    context: Context,
    workerParams: WorkerParameters,
) : Worker(context, workerParams) {

    private val channelId = "work_manager_channel"
    private var notificationBuilder: NotificationBuilder? = null

    override fun doWork(): Result {
        notificationBuilder = NotificationBuilder(applicationContext)
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
                priority = NotificationCompat.PRIORITY_DEFAULT,
            )
        }
        return Result.success()
    }

}