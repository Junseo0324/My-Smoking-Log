package com.devhjs.mysmokinglog.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.mysmokinglog.R
import com.devhjs.mysmokinglog.presentation.component.HomeButton
import com.devhjs.mysmokinglog.ui.AppColors
import com.devhjs.mysmokinglog.ui.AppTextStyles
import com.devhjs.mysmokinglog.presentation.util.getFormattedTimeAgo

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState,
    onAction: (HomeAction) -> Unit = {},
) {
    val (statusColor, statusBackgroundColor) = when (state.status) {
        SmokingStatus.SAFE -> AppColors.PrimaryColor to AppColors.PrimaryColor40
        SmokingStatus.WARNING -> AppColors.ThirdColor to AppColors.ThirdColor40
        SmokingStatus.EXCEEDED -> AppColors.SecondaryColor to AppColors.SecondaryColor40
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .padding(horizontal = 30.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(70.dp))
        Column(
            modifier = Modifier
                .size(150.dp)
                .background(color = statusBackgroundColor, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${state.todayCount}",
                style = AppTextStyles.titleTextBold.copy(color = statusColor),
            )
            Text(
                text = stringResource(R.string.home_curr_cig_unit),
                style = AppTextStyles.normalTextRegular.copy(color = AppColors.White),
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = stringResource(R.string.home_daily_limit_format, state.dailyLimit),
            style = AppTextStyles.normalTextRegular.copy(color = AppColors.White),
        )
        Spacer(modifier = Modifier.height(30.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            repeat(state.todayCount) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(statusColor)
                )
                Spacer(modifier = Modifier.width(5.dp))
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = AppColors.Black08, shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 10.dp, vertical = 10.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(
                            color = AppColors.Black10,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        tint = AppColors.White,
                        painter = painterResource(R.drawable.clock),
                        contentDescription = stringResource(R.string.home_clock_cd)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.home_last_smoked_label),
                        style = AppTextStyles.normalTextRegular.copy(color = AppColors.White),
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = getFormattedTimeAgo(state.lastSmokingTimestamp),
                        style = AppTextStyles.largeTextBold.copy(fontSize = 18.sp, color = AppColors.White),
                    )
                }
            }

        }
        Spacer(modifier = Modifier.height(30.dp))

        HomeButton(
            title = stringResource(R.string.home_add_cig_button),
            backgroundColor = AppColors.White,
            textColor = AppColors.Black
        ) {
            onAction(HomeAction.AddSmoking)
        }
        Spacer(modifier = Modifier.height(30.dp))

        AnimatedVisibility(
            visible = state.isUndoVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            HomeButton(
                title = stringResource(R.string.home_undo_button),
                backgroundColor = AppColors.Black30,
                textColor = AppColors.White
            ) {
                onAction(HomeAction.DeleteSmoking)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        state = HomeState(
            todayCount = 10,
            dailyLimit = 20,
            lastSmokingTimestamp = System.currentTimeMillis() - 3600000,
        )
    )
}