package com.app.func.features.scheduler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.app.func.utils.Logger

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        Toast.makeText(context, "AlarmReceiver - Alarm triggered!", Toast.LENGTH_SHORT).show()
        Logger.d("AlarmScheduler - Alarm scheduled for: AlarmReceiver - Alarm triggered!")
        // Trigger your Worker
        val workRequest = OneTimeWorkRequest.Builder(SchedulerNotificationWorker::class.java)
            .build()
        WorkManager.getInstance(context).enqueue(workRequest)
    }
}
