package com.devhjs.mysmokinglog.presentation.stat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.mysmokinglog.presentation.component.DailySmokingTimeChart
import com.devhjs.mysmokinglog.presentation.component.SmokingLogCard
import com.devhjs.mysmokinglog.presentation.component.StatCardHeader
import com.devhjs.mysmokinglog.presentation.component.WeeklyBarChart
import com.devhjs.mysmokinglog.ui.AppColors
import com.devhjs.mysmokinglog.ui.AppTextStyles

@Composable
fun StatScreen(
    modifier: Modifier = Modifier,
    state: StatState = StatState()
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .padding(horizontal = 30.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "통계",
            style = AppTextStyles.titleTextBold.copy(fontSize = 24.sp, color = AppColors.White)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SmokingLogCard(
                modifier = modifier.weight(1f)
            ) {
                StatCardHeader(
                    title = "기록 스트릭",
                    state = "${state.streak}",
                    description = "일 연속"
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            SmokingLogCard(
                modifier = modifier.weight(1f)
            ) {
                StatCardHeader(
                    title = "최장 미흡연",
                    state = "${state.longestStreak}",
                    description = "시간"
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        SmokingLogCard {
            StatCardHeader(
                title = "이번 달 흡연 비용",
                state = "₩ ${state.thisMonthCost}",
                description = "총 ${state.cigarettesTotalCount} 개비 (${state.packCount} 갑)"
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        SmokingLogCard {

            WeeklyBarChart(
                weeklyData = state.weeklyCigarettes
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        SmokingLogCard {
            DailySmokingTimeChart(
                hourlyData = state.todayTime
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun StatScreenPreview() {
    StatScreen(
        state = StatState(
            streak = 10,
            longestStreak = 15,
            thisMonthCost = 100000,
            cigarettesTotalCount = 100,
            packCount = 10,
            weeklyCigarettes = listOf(1, 2, 3, 4, 5, 6, 7),
            todayTime = listOf(
                1, 2, 3, 4, 0, 6, 7
            )

        )
    )
}