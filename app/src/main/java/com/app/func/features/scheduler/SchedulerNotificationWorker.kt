package com.app.func.features.scheduler

import android.content.Context
import android.content.Intent
import androidx.work.Worker
import androidx.work.WorkerParameters
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
        val intent = Intent(applicationContext, NotificationService::class.java)
        applicationContext.startService(intent)
        return Result.success()
    }

}