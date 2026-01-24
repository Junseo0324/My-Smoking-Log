package com.devhjs.mysmokinglog.presentation.widget.worker

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.devhjs.mysmokinglog.presentation.widget.SmokeLogWidget
import com.devhjs.mysmokinglog.presentation.widget.WidgetServiceLocator

class DailyWidgetUpdateWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val useCase = WidgetServiceLocator.getTodaySmokingWidgetUseCase(context)
            val state = useCase.execute()

            val manager = GlanceAppWidgetManager(context)
            val glanceIds = manager.getGlanceIds(SmokeLogWidget::class.java)

            glanceIds.forEach { glanceId ->
                updateAppWidgetState(
                    context = context,
                    definition = PreferencesGlanceStateDefinition,
                    glanceId = glanceId
                ) { prefs ->
                    prefs.toMutablePreferences().apply {
                         this[SmokeLogWidget.countKey] = state.count
                         this[SmokeLogWidget.lastTimestampKey] = state.lastSmokingTimestamp ?: 0L
                    }
                }
                SmokeLogWidget().update(context, glanceId)
            }
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}
