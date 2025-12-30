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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
    val context = LocalContext.current
    val pricePresets = context.resources.getIntArray(R.array.price_presets)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = stringResource(R.string.settings_title),
            style = AppTextStyles.titleTextBold.copy(fontSize = 24.sp, color = AppColors.White)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.settings_subtitle),
            style = AppTextStyles.normalTextRegular.copy(color = AppColors.Gray)
        )

        Spacer(modifier = Modifier.height(30.dp))

        SmokingLogCard {
            Column {
                SettingsCardHeader(
                    painter = painterResource(R.drawable.check),
                    title = stringResource(R.string.settings_daily_goal_title),
                    subtitle = stringResource(R.string.settings_daily_goal_subtitle),
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
                            text = "${state.dailyLimit} ${stringResource(R.string.unit_cig)}",
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
                        text = stringResource(R.string.settings_unit_1),
                        style = AppTextStyles.smallTextRegular.copy(color = AppColors.Gray)
                    )
                    Text(
                        text = stringResource(R.string.settings_unit_100),
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
                    title = stringResource(R.string.settings_cost_title),
                    subtitle = stringResource(R.string.settings_cost_subtitle),
                    iconTint = AppColors.ThirdColor
                )

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(R.string.settings_cost_input_hint),
                    style = AppTextStyles.smallTextRegular.copy(color = AppColors.Gray, fontSize = 12.sp)
                )
                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AppColors.Black15, RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = java.util.Currency.getInstance(java.util.Locale.getDefault()).symbol + " ",
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
                    pricePresets.forEach { price ->
                        PricePresetButton(
                            price = price,
                            isSelected = state.packPrice == price,
                            onClick = { onAction(SettingsAction.ChangePackPrice(price)) },
                            modifier = Modifier.weight(1f)
                        )
                    }
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
                        text = stringResource(R.string.settings_note_title),
                        style = AppTextStyles.normalTextBold.copy(color = AppColors.White)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                InfoItem(stringResource(R.string.settings_note_daily_goal))
                InfoItem(stringResource(R.string.settings_note_health))
                InfoItem(stringResource(R.string.settings_note_autosave))
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
                text = stringResource(R.string.settings_app_name),
                style = AppTextStyles.normalTextRegular.copy(color = AppColors.Gray)
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = "${stringResource(R.string.settings_version_prefix)}${state.appVersion}",
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