package com.devhjs.mysmokinglog.presentation.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent

class SmokeLogWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val useCase = WidgetServiceLocator.getTodaySmokingWidgetUseCase(context)

        val state = useCase.execute()

        provideContent {
            SmokeLogWidgetContent(
                count = state.count,
                lastTime = state.lastSmokingTime
            )
        }
    }
}
