package com.example.countdownservices

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class CountdownService : Service() {
    private val serviceScope = CoroutineScope(Dispatchers.Default + Job())

    companion object {
        private const val TAG = "CountdownService"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val countdownTime = intent?.getIntExtra("countdown_time", 0) ?: 0
        Log.d(TAG, "Starting countdown from $countdownTime seconds")
        startCountdown(countdownTime)
        return START_NOT_STICKY
    }

    private fun startCountdown(time: Int) {
        serviceScope.launch {
            for (i in time downTo 0) {
                Log.d(TAG, "Time remaining: $i seconds")
                delay(1000) // Wait for 1 second
            }
            Log.d(TAG, "Countdown finished!")
            stopSelf()
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        Log.d(TAG, "Service destroyed")
    }
}