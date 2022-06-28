package com.kromer.taskexecuter

import android.content.Context
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    private const val TAG = "Utils"
    private const val DELAY_TIME_MILLIS: Long = 3000
    private const val SIMPLE_DATE_FORMAT: String = "dd/MM/yyyy HH:mm"

    /**
     * Method for sleeping for a fixed amount of time to emulate slower work
     */
    fun sleep() {
        try {
            Thread.sleep(DELAY_TIME_MILLIS, 0)
        } catch (e: InterruptedException) {
            Log.e(TAG, e.message.toString())
        }
    }

    /**
     * Method for converting timestamp to formatted date
     */
    fun getDate(timestamp: Long, context: Context): String? {
        val sdf = SimpleDateFormat(SIMPLE_DATE_FORMAT, context.resources.configuration.locale)
        val netDate = Date(timestamp)
        return sdf.format(netDate)
    }
}