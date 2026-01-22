package com.devhjs.mysmokinglog.presentation.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.state.PreferencesGlanceStateDefinition

class AddSmokeAction : ActionCallback {

    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        WidgetServiceLocator.addSmokingUseCase(context).execute()

        val infoUseCase = WidgetServiceLocator.getTodaySmokingWidgetUseCase(context)
        val state = infoUseCase.execute()

        val manager = GlanceAppWidgetManager(context)
        val widget = SmokeLogWidget()
        val glanceIds = manager.getGlanceIds(widget.javaClass)

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
            widget.update(context, glanceId)
        }

    }
}