package com.kromer.taskexecuter

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import java.util.concurrent.Executors

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        WorkManager.initialize(
            this,
            Configuration.Builder()
                // Uses a fixed thread pool of size 2 threads.
                .setExecutor(Executors.newFixedThreadPool(2))
                .build()
        )
    }
}