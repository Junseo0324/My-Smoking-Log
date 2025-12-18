package com.devhjs.mysmokinglog.data.entity


import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "smoking_event",
    indices = [
        Index(value = ["timestamp"]),
        Index(value = ["date"])
    ]
)
data class SmokingEventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    /** 흡연 시각 (epoch millis) */
    val timestamp: Long,

    /** 날짜 문자열 (yyyy-MM-dd) */
    val date: String
)
