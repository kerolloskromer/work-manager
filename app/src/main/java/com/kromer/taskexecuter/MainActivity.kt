package com.kromer.taskexecuter

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.work.WorkInfo
import com.kromer.taskexecuter.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: MainViewModel by viewModels { MainViewModel.MainViewModelFactory(application) }

    override fun getVBInflater(): (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.tvLog.movementMethod = ScrollingMovementMethod()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnTask1.setOnClickListener {
            viewModel.startTask("Task #1").observe(this, outputWorkInfoObserver())
        }

        binding.btnTask2.setOnClickListener {
            viewModel.startTask("Task #2").observe(this, outputWorkInfoObserver())
        }

        binding.btnTask3.setOnClickListener {
            viewModel.startTask("Task #3").observe(this, outputWorkInfoObserver())
        }

        binding.btnTask4.setOnClickListener {
            viewModel.startTask("Task #4").observe(this, outputWorkInfoObserver())
        }
    }

    private fun outputWorkInfoObserver(): Observer<WorkInfo> {
        return Observer { workInfo ->
            if (workInfo.state.isFinished) {
                binding.progressBar.visibility = View.GONE

                val taskTitle = workInfo.outputData.getString(TaskWorker.KEY_TASK_TITLE)
                val taskTimestamp = workInfo.outputData.getLong(TaskWorker.KEY_TASK_TIMESTAMP, 0)
                binding.tvLog.append("\n ${Utils.getDate(taskTimestamp, this@MainActivity)} $taskTitle")
            } else {
                binding.progressBar.visibility = View.VISIBLE
            }
        }
    }
}