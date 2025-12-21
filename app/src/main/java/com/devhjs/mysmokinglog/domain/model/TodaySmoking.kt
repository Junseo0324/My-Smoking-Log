package com.devhjs.mysmokinglog.domain.model

data class TodaySmoking(
    val count: Int,
    val dailyLimit: Int,
    val lastSmokingTime: String,
)
