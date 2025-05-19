package com.app.func.features.scheduler

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.app.func.MainActivity
import com.app.func.notification.NotificationBuilder
import java.util.Calendar

class SchedulerNotificationWorker(
    context: Context,
    workerParameters: WorkerParameters,
) : Worker(context, workerParameters) {

    override fun doWork(): Result {
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        // Skip Saturday and Sunday
        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
            return Result.success()
        }
        //If only send notification, It do not need this Service
        /*val intent = Intent(applicationContext, NotificationService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            applicationContext.startForegroundService(intent)
        } else {
            applicationContext.startService(intent)
        }*/
        val openIntent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            applicationContext, 0, openIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notificationBuilder = NotificationBuilder(applicationContext)
        notificationBuilder.apply {
            createNotificationChannel(
                chanelId = "AlarmDaily",
                channelName = "AlarmDaily",
                importance = NotificationManagerCompat.IMPORTANCE_DEFAULT
            )
            notificationBuilder.sendNotification(
                chanelId = "AlarmDaily",
                contentTitle = "AlarmDaily",
                pendingIntent = pendingIntent,
            )
        }
        return Result.success()
    }
}