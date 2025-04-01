package com.app.func.notification

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.Style

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
        smallIcon: Int,
        contentTitle: String = "Notification Title",
        contentText: String = "This is the notification content.",
        style: Style? = null,
        notifyId: Int = 1,
        priority: Int = NotificationCompat.PRIORITY_DEFAULT,
    )
}