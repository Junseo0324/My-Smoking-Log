package com.devhjs.mysmokinglog.presentation.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.mysmokinglog.R
import com.devhjs.mysmokinglog.presentation.component.CustomSlider
import com.devhjs.mysmokinglog.presentation.component.InfoItem
import com.devhjs.mysmokinglog.presentation.component.PricePresetButton
import com.devhjs.mysmokinglog.presentation.component.SettingsCardHeader
import com.devhjs.mysmokinglog.presentation.component.SmokingLogCard
import com.devhjs.mysmokinglog.ui.AppColors
import com.devhjs.mysmokinglog.ui.AppTextStyles

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    state: SettingsState = SettingsState(),
    onAction: (SettingsAction) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "설정",
            style = AppTextStyles.titleTextBold.copy(fontSize = 24.sp, color = AppColors.White)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "목표와 가격을 설정하세요",
            style = AppTextStyles.normalTextRegular.copy(color = AppColors.Gray)
        )

        Spacer(modifier = Modifier.height(30.dp))

        SmokingLogCard {
            Column {
                SettingsCardHeader(
                    painter = painterResource(R.drawable.check),
                    title = "하루 상한선",
                    subtitle = "하루 최대 흡연 개비 수",
                    iconTint = AppColors.PrimaryColor
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomSlider(
                        modifier = Modifier.weight(1f),
                        value = state.dailyLimit.toFloat(),
                        onValueChange = { onAction(SettingsAction.ChangeDailyLimit(it.toInt())) },
                        onValueChangeFinished = { onAction(SettingsAction.SaveSettings) },
                        valueRange = 1f..100f,
                        activeColor = AppColors.PrimaryColor,
                        inactiveColor = AppColors.Black20
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Box(
                        modifier = Modifier
                            .background(color = AppColors.Black20, shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "${state.dailyLimit} 개비",
                            style = AppTextStyles.smallTextBold.copy(color = AppColors.White)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "1개비",
                        style = AppTextStyles.smallTextRegular.copy(color = AppColors.Gray)
                    )
                    Text(
                        text = "100개비",
                        style = AppTextStyles.smallTextRegular.copy(color = AppColors.Gray)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        SmokingLogCard {
            Column {
                SettingsCardHeader(
                    painter = painterResource(R.drawable.money),
                    title = "한 갑 가격",
                    subtitle = "담배 한 갑의 가격",
                    iconTint = AppColors.ThirdColor
                )

                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AppColors.Black15, RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "₩ ",
                            style = AppTextStyles.titleTextBold.copy(
                                fontSize = 20.sp,
                                color = AppColors.White
                            )
                        )
                        BasicTextField(
                            value = if (state.packPrice == 0) "" else state.packPrice.toString(),
                            onValueChange = { newValue ->
                                if (newValue.isEmpty()) {
                                    onAction(SettingsAction.ChangePackPrice(0))
                                } else {
                                    newValue.filter { it.isDigit() }.toIntOrNull()?.let {
                                        onAction(SettingsAction.ChangePackPrice(it))
                                    }
                                }
                            },
                            textStyle = AppTextStyles.titleTextBold.copy(
                                fontSize = 20.sp,
                                color = AppColors.White
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = { onAction(SettingsAction.SaveSettings) }
                            ),
                            singleLine = true,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PricePresetButton(
                        price = 4500,
                        isSelected = state.packPrice == 4500,
                        onClick = { onAction(SettingsAction.ChangePackPrice(4500)) },
                        modifier = Modifier.weight(1f)
                    )
                    PricePresetButton(
                        price = 5000,
                        isSelected = state.packPrice == 5000,
                        onClick = { onAction(SettingsAction.ChangePackPrice(5000)) },
                        modifier = Modifier.weight(1f)
                    )
                    PricePresetButton(
                        price = 5500,
                        isSelected = state.packPrice == 5500,
                        onClick = { onAction(SettingsAction.ChangePackPrice(5500)) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        SmokingLogCard {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = AppColors.Notification,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "알아두세요",
                        style = AppTextStyles.normalTextBold.copy(color = AppColors.White)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                InfoItem("상한선은 경고를 위한 기준이며 강제되지 않습니다")
                InfoItem("가격은 월간 비용 계산에 사용됩니다")
                InfoItem("설정은 자동으로 저장됩니다")
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "My Smoking Log",
                style = AppTextStyles.normalTextRegular.copy(color = AppColors.Gray)
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = "v1.0.0",
                style = AppTextStyles.smallTextRegular.copy(color = AppColors.Gray)
            )
        }
    }
}

@Preview
@Composable
private fun SettingScreenPreview() {
    SettingScreen()
}