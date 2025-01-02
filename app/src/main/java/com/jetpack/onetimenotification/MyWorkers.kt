package com.jetpack.onetimenotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorkers(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {

    companion object {
        private const val CHANNEL_ID = "0xx"
        private const val CHANNEL_NAME = "TESTING NOTIFICATION"
        private const val NOTIFICATION_ID = 1
        const val DESC = "Desc"
    }

    override fun doWork(): Result {
        val description = inputData.getString(DESC)
        if (description != null) {
            showNotification("One Time Notification", description)
        }
        return Result.success()
    }

    private fun showNotification(title: String, description: String) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationCompat : NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
        .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationCompat.setChannelId(channel.id)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(NOTIFICATION_ID, notificationCompat.build())
    }
}