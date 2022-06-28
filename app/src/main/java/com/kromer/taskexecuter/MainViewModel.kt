package com.kromer.taskexecuter

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager

class MainViewModel(application: Application) : ViewModel() {

    private val workManager = WorkManager.getInstance(application)

    /**
     * Create the WorkRequest to process the task and append it to the queue
     */
    fun startTask(title: String): LiveData<WorkInfo> {
        // build data input
        val dataBuilder = Data.Builder()
        dataBuilder.putString(TaskWorker.KEY_TASK_TITLE, title)

        // build task worker
        val taskBuilder = OneTimeWorkRequestBuilder<TaskWorker>()
        taskBuilder.setInputData(dataBuilder.build())
        val request = taskBuilder.build()

        // Add WorkRequest to the queue
        workManager.enqueue(
            request
        )

        return workManager.getWorkInfoByIdLiveData(request.id)
    }

    class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                MainViewModel(application) as T
            } else {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}
