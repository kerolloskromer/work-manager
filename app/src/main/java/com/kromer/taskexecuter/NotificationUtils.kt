package com.kromer.taskexecuter

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*

object NotificationUtils {
    private const val CHANNEL_ID = "TASK_NOTIFICATION"

    fun showNotification(title: String, message: String, context: Context): Int {
        // Make a channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            val name = "WorkManager Notifications"
            val description = "Shows notifications whenever work starts"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description

            // Add the channel
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            notificationManager?.createNotificationChannel(channel)
        }

        // Create the notification
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))

        val notificationId = UUID.randomUUID().hashCode()

        // Show the notification
        NotificationManagerCompat.from(context).notify(notificationId, builder.build())

        // return notificationId to be used later to cancel it
        return notificationId
    }

    fun dismissNotification(notificationId: Int, context: Context) {
        NotificationManagerCompat.from(context).cancel(notificationId)
    }
}