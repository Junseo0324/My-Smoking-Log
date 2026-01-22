package com.devhjs.mysmokinglog.presentation.util

import com.devhjs.mysmokinglog.R
import com.devhjs.mysmokinglog.core.util.UiText
import java.text.NumberFormat
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Locale

object TimeFormatter {

    fun formatTimeAgo(timestamp: Long): UiText {
        val now = Instant.now()
        val eventTime = Instant.ofEpochMilli(timestamp)
        val minutes = ChronoUnit.MINUTES.between(eventTime, now)

        return when {
            minutes < 1 -> UiText.StringResource(R.string.time_just_now)
            minutes < 60 -> UiText.StringResource(R.string.time_fmt_mins_ago, minutes)
            minutes < 1440 -> UiText.StringResource(R.string.time_fmt_hours_ago, minutes / 60)
            else -> UiText.StringResource(R.string.time_fmt_days_ago, minutes / 1440)
        }
    }

    fun formatDuration(hours: Long): UiText {
        if (hours == 0L) return UiText.StringResource(R.string.duration_fmt_hours, 0)
        
        val days = hours / 24
        val remainingHours = hours % 24

        return if (days > 0) {
            if (remainingHours > 0) {
                UiText.StringResource(R.string.duration_fmt_days_hours, days, remainingHours)
            } else {
                UiText.StringResource(R.string.duration_fmt_days, days)
            }
        } else {
             UiText.StringResource(R.string.duration_fmt_hours, hours)
        }
    }
    
    fun formatAverageInterval(minutes: Long?): UiText {
        return if (minutes != null) {
             UiText.DynamicString("$minutes")
        } else {
             UiText.StringResource(R.string.duration_none)
        }
    }

    fun formatCurrency(amount: Int): UiText {
        val formatted = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(amount)
        return UiText.DynamicString(formatted)
    }
}
