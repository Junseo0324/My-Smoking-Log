package com.devhjs.mysmokinglog.presentation.util

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.devhjs.mysmokinglog.R
import java.text.NumberFormat
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Currency
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
object TimeFormatter {

    fun formatTimeAgo(context: Context, timestamp: Long): String {
        val now = Instant.now()
        val eventTime = Instant.ofEpochMilli(timestamp)
        val minutes = ChronoUnit.MINUTES.between(eventTime, now)

        return when {
            minutes < 1 -> context.getString(R.string.time_just_now)
            minutes < 60 -> context.getString(R.string.time_fmt_mins_ago, minutes)
            minutes < 1440 -> context.getString(R.string.time_fmt_hours_ago, minutes / 60)
            else -> context.getString(R.string.time_fmt_days_ago, minutes / 1440)
        }
    }

    fun formatDuration(context: Context, hours: Long): String {
        if (hours == 0L) return context.getString(R.string.duration_fmt_hours, 0)
        
        val days = hours / 24
        val remainingHours = hours % 24

        return if (days > 0) {
            if (remainingHours > 0) {
                context.getString(R.string.duration_fmt_days_hours, days, remainingHours)
            } else {
                context.getString(R.string.duration_fmt_days, days)
            }
        } else {
             context.getString(R.string.duration_fmt_hours, hours)
        }
    }
    
    fun formatAverageInterval(context: Context, minutes: Long?): String {
        return if (minutes != null) {
             "$minutes"
        } else {
             context.getString(R.string.duration_none)
        }
    }

    fun formatCurrency(amount: Int): String {
        return NumberFormat.getCurrencyInstance(Locale.getDefault()).format(amount)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun getFormattedTimeAgo(timestamp: Long?): String {
    if (timestamp == null) return ""
    val context = LocalContext.current
    return TimeFormatter.formatTimeAgo(context, timestamp)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun getFormattedCurrency(amount: Int): String {
    return TimeFormatter.formatCurrency(amount)
}
