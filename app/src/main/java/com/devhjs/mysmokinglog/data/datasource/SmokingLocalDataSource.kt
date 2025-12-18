package com.devhjs.mysmokinglog.data.datasource

import com.devhjs.mysmokinglog.data.entity.SmokingEventEntity

interface SmokingLocalDataSource {
    suspend fun insertEvent(event: SmokingEventEntity)

    suspend fun deleteEvent(event: SmokingEventEntity)

    suspend fun getEventsByDate(date: String): List<SmokingEventEntity>

    suspend fun getLastEvent(): SmokingEventEntity?

    suspend fun getEventsBetween(startDate: String, endDate: String): List<SmokingEventEntity>
}