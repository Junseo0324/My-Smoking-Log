package com.devhjs.mysmokinglog.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.mysmokinglog.R
import com.devhjs.mysmokinglog.ui.AppColors

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState,
    onAction: (HomeAction) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(70.dp))
        Column(
            modifier = Modifier
                .size(150.dp)
                .background(color = Color(0x8034D399), shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // text style 정의 후 변경
            Text(
                text = "${state.todayCount}",
                fontSize = 50.sp,
                fontWeight = FontWeight.SemiBold,
                color = AppColors.White,
            )
            Text(
                text = "개비",
                color = AppColors.White
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "하루 상한선 : ${state.dailyLimit}개비",
            color = AppColors.White
        )
        Spacer(modifier = Modifier.height(15.dp))
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
                        .background(Color(0xFF34D399))
                )
                Spacer(modifier = Modifier.width(5.dp))
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = AppColors.CardColor, shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 10.dp, vertical = 10.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(
                            color = AppColors.Background,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        tint = AppColors.White,
                        painter = painterResource(R.drawable.clock),
                        contentDescription = "시계"
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "마지막으로 핀 지",
                        color = AppColors.White
                    )
                    Text(
                        text = "${state.lastSmokingTime}",
                        color = AppColors.White
                    )
                }
            }

        }
        Spacer(modifier = Modifier.height(15.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = AppColors.White, shape = RoundedCornerShape(10.dp))
                .clickable {
                    onAction(HomeAction.AddSmoking)
                }
                .padding(vertical = 20.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+ 한 개비",
                fontSize = 20.sp
            )
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
            lastSmokingTime = "1시간 전"
        )
    )
}