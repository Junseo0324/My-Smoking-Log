package com.devhjs.mysmokinglog.core.util

import java.time.Clock
import java.time.Instant
import java.time.temporal.ChronoUnit

fun formatTimeAgo(
    timestamp: Long,
    clock: Clock
): String {
    val now = Instant.now(clock)
    val eventTime = Instant.ofEpochMilli(timestamp)

    val minutes = ChronoUnit.MINUTES.between(eventTime, now)

    return when {
        minutes < 1 -> "방금 전"
        minutes < 60 -> "${minutes}분 전"
        minutes < 1440 -> "${minutes / 60}시간 전"
        else -> "${minutes / 1440}일 전"
    }
}