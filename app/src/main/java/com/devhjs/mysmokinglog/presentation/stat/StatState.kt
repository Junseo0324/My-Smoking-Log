package com.devhjs.mysmokinglog.presentation.stat

import androidx.compose.runtime.Stable
import com.devhjs.mysmokinglog.core.util.UiText

@Stable
data class StatState(
    val streak: UiText = UiText.DynamicString("0"),
    val averageSmokingInterval: UiText = UiText.DynamicString("-"),
    val longestStreak: UiText = UiText.DynamicString("0"),
    val thisMonthCost: UiText = UiText.DynamicString("0"),
    val cigarettesTotalCount: Int = 0,
    val packCount: Int = 0,
    val weeklyCigarettes: List<Int> = emptyList(),
    val todayTime: List<Int> = emptyList()
)
