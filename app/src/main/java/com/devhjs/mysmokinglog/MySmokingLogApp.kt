package com.devhjs.mysmokinglog

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.devhjs.mysmokinglog.presentation.widget.worker.DailyWidgetUpdateWorker
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class MySmokingLogApp : Application() {
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
        scheduleDailyWidgetUpdate()
    }

    private fun scheduleDailyWidgetUpdate() {
        val workRequest = PeriodicWorkRequestBuilder<DailyWidgetUpdateWorker>(
            1, TimeUnit.HOURS
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "DailyWidgetUpdate",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}