package com.devhjs.mysmokinglog.presentation.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.Button
import androidx.glance.ButtonDefaults
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider

@Composable
fun SmokeLogWidgetContent(
    count: Int = 3,
    lastTime: String = "1시간 전",
) {
    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(ColorProvider(Color(0x800A0A0E)))
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "오늘의 흡연",
            modifier = GlanceModifier.padding(bottom = 5.dp),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = ColorProvider(Color.White)
            )
        )


        Text(
            text = "$count 개비",
            modifier = GlanceModifier.padding(bottom = 5.dp),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = ColorProvider(color = Color(0xFF34D399))
            )
        )

        Text(
            text = lastTime,
            modifier = GlanceModifier.padding(bottom = 8.dp),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = ColorProvider(Color.White)
            )
        )

        Button(
            text = "+",
            modifier = GlanceModifier.padding(8.dp).fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ColorProvider(Color(0xFFFFFFFF)),
                contentColor = ColorProvider(Color(0xFF000000))
            ),
            onClick = actionRunCallback<AddSmokeAction>()
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun SmokieLogWidgetContentPreview() {
    SmokeLogWidgetContent()
}