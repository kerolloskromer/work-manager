package com.kromer.taskexecuter

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class TaskWorker(private val ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    companion object {
        const val TAG = "TaskWorker"
        const val KEY_TASK_TITLE = "KEY_TASK_TITLE"
        const val KEY_TASK_TIMESTAMP = "KEY_TASK_TIMESTAMP"
    }

    override fun doWork(): Result {
        // receive input data
        val title = inputData.getString(KEY_TASK_TITLE) ?: ""
        // show notification indicating the current task
        val notificationId =
            NotificationUtils.showNotification(title, "$title is in progress...", ctx)
        // emulate slower work.
        Utils.sleep()
        return try {
            val outputData = workDataOf(
                KEY_TASK_TITLE to title,
                KEY_TASK_TIMESTAMP to System.currentTimeMillis()
            )
            NotificationUtils.dismissNotification(notificationId, ctx)
            Result.success(outputData)
        } catch (throwable: Throwable) {
            Log.e(TAG, "Error processing task")
            NotificationUtils.dismissNotification(notificationId, ctx)
            Result.failure()
        }
    }
}