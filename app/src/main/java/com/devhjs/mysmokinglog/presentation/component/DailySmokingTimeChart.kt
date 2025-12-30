package com.devhjs.mysmokinglog.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import com.devhjs.mysmokinglog.R
import com.devhjs.mysmokinglog.ui.AppColors
import com.devhjs.mysmokinglog.ui.AppTextStyles

@Composable
fun DailySmokingTimeChart(
    hourlyData: List<Int>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.chart_daily_title),
            style = AppTextStyles.titleTextBold.copy(fontSize = 18.sp, color = AppColors.White)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = stringResource(R.string.chart_daily_desc),
            style = AppTextStyles.smallTextRegular.copy(color = AppColors.White)
        )
        Spacer(modifier = Modifier.height(20.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf(0, 6, 12, 18, 24).forEach { time ->
                Text(
                    text = stringResource(R.string.chart_time_format, time),
                    style = AppTextStyles.smallTextRegular.copy(color = AppColors.Gray80)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        ) {
            val totalWidth = size.width
            val dotRadius = 6.dp.toPx()
            val dotDiameter = dotRadius * 2
            val usableWidth = totalWidth - dotDiameter // Reserve space for first and last half-dots
            val step = usableWidth / 23f
            

            for (i in 0 until 24) {
                val cx = dotRadius + (i * step)
                val cy = size.height / 2
                
                val count = hourlyData.getOrElse(i) { 0 }
                val color = if (count > 0) {
                    AppColors.PrimaryColor
                } else {
                    AppColors.Black20
                }
                
                drawCircle(
                    color = color,
                    radius = dotRadius,
                    center = Offset(cx, cy)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.chart_freq_title),
                style = AppTextStyles.smallTextRegular.copy(color = AppColors.Gray)
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(10.dp).background(AppColors.Black20, CircleShape))
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = stringResource(R.string.chart_freq_low), style = AppTextStyles.smallTextRegular.copy(color = AppColors.Gray))
                
                Spacer(modifier = Modifier.size(12.dp))
                
                Box(modifier = Modifier.size(10.dp).background(AppColors.PrimaryColor, CircleShape))
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = stringResource(R.string.chart_freq_high), style = AppTextStyles.smallTextRegular.copy(color = AppColors.Gray))
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
private fun DailySmokingTimeChartPreview() {
    val mockData = List(24) { if (it == 18) 5 else 0 }
    DailySmokingTimeChart(hourlyData = mockData)
}
