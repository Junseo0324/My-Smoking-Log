package com.devhjs.mysmokinglog.presentation.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.Button
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider

@Composable
fun SmokeLogWidgetContent(
    count: Int = 3,
    lastTime: String = "1시간 전"
) {
    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(ColorProvider(Color(0x400A0A0E)))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = "오늘 흡연",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = ColorProvider(Color.White)
            )
        )

        Text(
            text = "$count 회",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = ColorProvider(Color.White)
            )
        )

        Text(
            text = "마지막: $lastTime",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = ColorProvider(Color.White)
            )
        )

        Spacer(GlanceModifier.height(8.dp))

        Button(
            text = "+",
            onClick = actionRunCallback<AddSmokeAction>()
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun SmokieLogWidgetContentPreview() {
    SmokeLogWidgetContent()
}