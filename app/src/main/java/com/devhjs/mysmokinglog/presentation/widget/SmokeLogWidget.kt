package com.devhjs.mysmokinglog.presentation.widget

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.currentState
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition

class SmokeLogWidget : GlanceAppWidget() {

    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val prefs = currentState<Preferences>()
            
            val count = prefs[countKey] ?: 0
            val lastTime = prefs[lastTimeKey] ?: ""

            SmokeLogWidgetContent(
                count = count,
                lastTime = lastTime
            )
        }
    }

    companion object {
        val countKey = intPreferencesKey("count")
        val lastTimeKey = stringPreferencesKey("last_time")
    }
}
