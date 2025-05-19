package com.app.func.notification

import android.app.PendingIntent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.Style
import com.app.func.R

interface INotificationBuilder {

    fun getNotificationId(): Int

    fun createNotificationChannel(
        chanelId: String = "CHANEL_ID",
        channelName: String = "Channel Name",
        channelDescription: String = "Channel Description",
        importance: Int,
    )

    fun sendNotification(
        chanelId: String = "CHANEL_ID",
        smallIcon: Int = R.drawable.ic_notify,
        contentTitle: String = "Notification Title",
        contentText: String = "This is the notification content.",
        style: Style? = null,
        pendingIntent: PendingIntent? = null,
        notifyId: Int = 1,
        priority: Int = NotificationCompat.PRIORITY_DEFAULT,
    )
}