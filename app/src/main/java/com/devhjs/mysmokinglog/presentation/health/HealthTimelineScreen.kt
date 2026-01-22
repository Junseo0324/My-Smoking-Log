package com.devhjs.mysmokinglog.presentation.health

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.mysmokinglog.R
import com.devhjs.mysmokinglog.presentation.component.HealthMilestoneItem
import com.devhjs.mysmokinglog.presentation.designsystem.AppColors
import com.devhjs.mysmokinglog.presentation.designsystem.AppTextStyles

@Composable
fun HealthTimelineScreen(
    state: HealthTimelineState = HealthTimelineState(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.health_timeline_title),
            style = AppTextStyles.titleTextBold.copy(fontSize = 24.sp, color = AppColors.White)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = state.timeSinceLastSmoking.asString(),
            style = AppTextStyles.normalTextRegular.copy(color = AppColors.Gray)
        )
        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            contentPadding = PaddingValues(bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.milestones) { milestone ->
                HealthMilestoneItem(milestone = milestone)
            }
        }
    }
}

