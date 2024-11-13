package com.example.countdownservices

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.countdownservices.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startCountdownButton.setOnClickListener {
            val countdownTimeStr = binding.countdownInput.text.toString()
            if (countdownTimeStr.isBlank()) {
                Toast.makeText(this, "Please enter a time", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val countdownTime = countdownTimeStr.toIntOrNull() ?: 0
            if (countdownTime > 0) {
                startCountdownService(countdownTime)
                Toast.makeText(this, "Countdown started! Check Logcat for progress",
                    Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Please enter a valid time", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startCountdownService(time: Int) {
        val intent = Intent(this, CountdownService::class.java).apply {
            putExtra("countdown_time", time)
        }
        startService(intent)
    }
}