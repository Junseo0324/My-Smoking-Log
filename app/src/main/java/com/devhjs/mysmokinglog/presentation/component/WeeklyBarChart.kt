package com.devhjs.mysmokinglog.presentation.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.mysmokinglog.ui.AppColors
import com.devhjs.mysmokinglog.ui.AppTextStyles

@Composable
fun WeeklyBarChart(
    weeklyData: List<Int>,
    modifier: Modifier = Modifier
) {
    if (weeklyData.isEmpty()) return

    val days = listOf("월", "화", "수", "목", "금", "토", "일")
    val maxVal = weeklyData.maxOrNull()?.toFloat() ?: 10f
    val axisMax = if (maxVal == 0f) 10f else maxVal + (maxVal * 0.1f)
    
    val animatable = remember { Animatable(0f) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(weeklyData) {
        animatable.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 800,
                easing = LinearEasing
            )
        )
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "주간 흡연 패턴",
            style = AppTextStyles.titleTextBold.copy(fontSize = 18.sp, color = AppColors.White)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "* 일주일 간 흡연 패턴을 시각화하여 변화를 인식하는데 도움을 줍니다.",
            style = AppTextStyles.smallTextRegular.copy(color = AppColors.White)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(bottom = 20.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = axisMax.toInt().toString(),
                    style = AppTextStyles.smallTextRegular.copy(fontSize = 10.sp, color = AppColors.Gray80)
                )
                Text(
                    text = (axisMax / 2).toInt().toString(),
                    style = AppTextStyles.smallTextRegular.copy(fontSize = 10.sp, color = AppColors.Gray80)
                )
                Text(
                    text = "0",
                    style = AppTextStyles.smallTextRegular.copy(fontSize = 10.sp, color = AppColors.Gray80)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {

                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInput(Unit) {
                                detectTapGestures { offset ->
                                    val barWidth = size.width / 7
                                    val index = (offset.x / barWidth).toInt()
                                    if (index in 0..6) {
                                        selectedIndex = if (selectedIndex == index) null else index
                                    }
                                }
                            }
                    ) {
                        val barWidth = size.width / 7
                        val barSpacing = barWidth * 0.3f
                        val effectiveBarWidth = barWidth - barSpacing
                        
                        val gridColor = AppColors.Gray80.copy(alpha = 0.2f)
                        drawLine(
                            color = gridColor,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = 1.dp.toPx()
                        )
                        drawLine(
                            color = gridColor,
                            start = Offset(0f, size.height / 2),
                            end = Offset(size.width, size.height / 2),
                            strokeWidth = 1.dp.toPx()
                        )
                        drawLine(
                            color = gridColor,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 1.dp.toPx()
                        )

                        weeklyData.take(7).forEachIndexed { index, value ->
                            val barHeight = (value / axisMax) * size.height * animatable.value
                            
                            val x = (index * barWidth) + (barSpacing / 2)
                            val y = size.height - barHeight

                            val isSelected = selectedIndex == index
                            val barColor = if (isSelected) AppColors.PrimaryColor else AppColors.PrimaryColor.copy(alpha = 0.5f)

                            // Draw Bar
                            val path = Path().apply {
                                addRoundRect(
                                    RoundRect(
                                        rect = Rect(offset = Offset(x, y), size = Size(effectiveBarWidth, barHeight)),
                                        topLeft = CornerRadius(4.dp.toPx()),
                                        topRight = CornerRadius(4.dp.toPx()),
                                        bottomLeft = CornerRadius.Zero,
                                        bottomRight = CornerRadius.Zero
                                    )
                                )
                            }
                            drawPath(path, color = barColor)

                            if (isSelected) {
                                val text = "${value}개"
                                val paint = Paint().asFrameworkPaint().apply {
                                    isAntiAlias = true
                                    textSize = 12.sp.toPx()
                                    color = AppColors.White.toArgb()
                                    textAlign = android.graphics.Paint.Align.CENTER
                                }
                                
                                drawContext.canvas.nativeCanvas.drawText(
                                    text,
                                    x + effectiveBarWidth / 2,
                                    y - 10.dp.toPx(),
                                    paint
                                )
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    days.forEach { day ->
                        Text(
                            text = day,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            style = AppTextStyles.smallTextRegular.copy(
                                fontSize = 12.sp,
                                color = AppColors.Gray80
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeeklyBarChartPreview() {
    WeeklyBarChart(
        weeklyData = listOf(15, 8, 12, 5, 20, 10, 3)
    )
}
