package com.devhjs.mysmokinglog.presentation.stat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.mysmokinglog.R
import com.devhjs.mysmokinglog.presentation.component.DailySmokingTimeChart
import com.devhjs.mysmokinglog.presentation.component.SmokingLogCard
import com.devhjs.mysmokinglog.presentation.component.StatCardHeader
import com.devhjs.mysmokinglog.presentation.component.WeeklyBarChart
import com.devhjs.mysmokinglog.presentation.designsystem.AppColors
import com.devhjs.mysmokinglog.presentation.designsystem.AppTextStyles


@Composable
fun StatScreen(
    modifier: Modifier = Modifier,
    state: StatState = StatState(),
    onNavigateToHealthTimeline: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .padding(horizontal = 30.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.stat_title),
                style = AppTextStyles.titleTextBold.copy(fontSize = 24.sp, color = AppColors.White)
            )
            Text(
                text = stringResource(R.string.health_timeline_title),
                style = AppTextStyles.normalTextRegular.copy(fontSize = 14.sp, color = AppColors.Gray80),
                modifier = Modifier.clickable { onNavigateToHealthTimeline() }
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SmokingLogCard(
                modifier = modifier.weight(1f)
            ) {
                StatCardHeader(
                    title = stringResource(R.string.stat_streak_title),
                    state = state.streak.asString(),
                    description = stringResource(R.string.stat_streak_desc)
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            SmokingLogCard(
                modifier = modifier.weight(1f)
            ) {
                StatCardHeader(
                    title = stringResource(R.string.stat_avg_interval_title),
                    state = state.averageSmokingInterval.asString(),
                    description = stringResource(R.string.stat_avg_interval_desc)
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        SmokingLogCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            StatCardHeader(
                title = stringResource(R.string.stat_longest_break_title),
                state = state.longestStreak.asString(),
                description = stringResource(R.string.stat_longest_break_desc)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        SmokingLogCard {
            StatCardHeader(
                title = stringResource(R.string.stat_cost_title),
                state = state.thisMonthCost.asString(),
                description = stringResource(R.string.stat_cost_desc_format, state.cigarettesTotalCount, state.packCount)
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
    StatScreen()
}