package com.devhjs.mysmokinglog.presentation.home

data class HomeState(
    val isLoading: Boolean = false,
    val todayCount: Int = 0,
    val dailyLimit: Int = 0,
    val lastSmokingTime: String = ""
)
