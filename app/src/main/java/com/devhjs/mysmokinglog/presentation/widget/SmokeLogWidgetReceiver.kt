package com.devhjs.mysmokinglog.presentation.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.state.PreferencesGlanceStateDefinition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SmokeLogWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget = SmokeLogWidget()

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        
        CoroutineScope(Dispatchers.IO).launch {
            val useCase = WidgetServiceLocator.getTodaySmokingWidgetUseCase(context)
            val state = useCase.execute()
            
            val manager = GlanceAppWidgetManager(context)
            val glanceIds = manager.getGlanceIds(glanceAppWidget.javaClass)
            
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
                glanceAppWidget.update(context, glanceId)
            }
        }
    }
}
