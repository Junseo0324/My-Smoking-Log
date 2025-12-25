package com.devhjs.mysmokinglog.presentation.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.updateAll


class AddSmokeAction : ActionCallback {

    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        val useCase = WidgetServiceLocator.addSmokingUseCase(context)

        useCase.execute()

        // ✅ 위젯 다시 갱신
//        SmokeLogWidget().update(context, glanceId)
        SmokeLogWidget().updateAll(context)

    }
}