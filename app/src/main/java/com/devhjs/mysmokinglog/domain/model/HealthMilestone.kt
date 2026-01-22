package com.devhjs.mysmokinglog.domain.model

data class HealthMilestone(
    val minutesRequired: Long,
    val title: String,
    val description: String,
    val isAchieved: Boolean = false
)
