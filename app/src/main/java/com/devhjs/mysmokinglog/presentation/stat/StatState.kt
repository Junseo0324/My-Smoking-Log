package com.devhjs.mysmokinglog.presentation.stat

import androidx.compose.runtime.Stable

@Stable
data class StatState(
    val streak: Int = 0,
    val averageSmokingInterval: Long? = null, // In minutes
    val longestStreak: Long = 0, // In hours
    val thisMonthCost: Int = 0, // Raw cost
    val cigarettesTotalCount: Int = 0,
    val packCount: Int = 0,
    val weeklyCigarettes: List<Int> = emptyList(),
    val todayTime: List<Int> = emptyList()
)
