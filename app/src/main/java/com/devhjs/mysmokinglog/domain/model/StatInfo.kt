package com.devhjs.mysmokinglog.domain.model

data class StatInfo(
    val streak: Int = 0,
    val averageSmokingInterval: Long? = null,
    val longestStreak: Long = 0,
    val thisMonthCost: Int = 0,
    val cigarettesTotalCount: Int = 0,
    val packCount: Int = 0,
    val weeklyCigarettes: List<Int> = emptyList(),
    val todayTime: List<Int> = emptyList()
)
