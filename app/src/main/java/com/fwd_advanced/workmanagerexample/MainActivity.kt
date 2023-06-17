package com.fwd_advanced.workmanagerexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import com.fwd_advanced.workmanagerexample.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.oneTimeBT.setOnClickListener {
            oneTimeWork()
        }

        binding.periodicTimeBT.setOnClickListener {
            periodicTimeWork()
        }
    }

    private fun oneTimeWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresCharging(false)
            .build()

        val workRequest: WorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }

    private fun periodicTimeWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val workRequest = PeriodicWorkRequest.Builder(MyWorker::class.java, 15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .addTag("my_id")
            .build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork("my_id", ExistingPeriodicWorkPolicy.KEEP, workRequest)


    }
}