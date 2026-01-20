package com.devhjs.mysmokinglog.presentation.widget

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.currentState
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.devhjs.mysmokinglog.presentation.util.TimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class SmokeLogWidget : GlanceAppWidget() {

    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val prefs = currentState<Preferences>()
            
            val count = prefs[countKey] ?: 0
            val lastTimestamp = prefs[lastTimestampKey] ?: 0L
            
            val lastTime = if (lastTimestamp > 0) {
                 TimeFormatter.formatTimeAgo(lastTimestamp).asString(context)
            } else {
                 "-" 
            }

            SmokeLogWidgetContent(
                count = count,
                lastTime = lastTime
            )
        }
    }

    companion object {
        val countKey = intPreferencesKey("count")
        val lastTimestampKey = longPreferencesKey("last_timestamp")
    }
}
