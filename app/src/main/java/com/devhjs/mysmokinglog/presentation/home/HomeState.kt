package com.devhjs.mysmokinglog.presentation.home

data class HomeState(
    val isLoading: Boolean = false,
    val todayCount: Int = 0,
    val dailyLimit: Int = 0,
    val lastSmokingTimestamp: Long? = null,
    val status: SmokingStatus = SmokingStatus.SAFE,
    val isUndoVisible: Boolean = false,
    val observedDate: String = "",
)

enum class SmokingStatus {
    SAFE, WARNING, EXCEEDED
}
