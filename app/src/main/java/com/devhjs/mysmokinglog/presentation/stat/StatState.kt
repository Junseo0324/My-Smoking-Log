package com.devhjs.mysmokinglog.presentation.stat

import androidx.compose.runtime.Stable

@Stable
data class StatState(
    val streak: Int = 0,
    val longestStreak: Int = 0,
    val thisMonthCost: Int = 0,
    val cigarettesTotalCount: Int = 0,
    val packCount: Int = 0,
    val weeklyCigarettes: List<Int> = emptyList(),
    val todayTime: List<Int> = emptyList()
)
