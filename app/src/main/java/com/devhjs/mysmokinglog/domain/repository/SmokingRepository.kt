package com.devhjs.mysmokinglog.domain.repository

import com.devhjs.mysmokinglog.domain.model.SmokingEvent

interface SmokingRepository {
    suspend fun insert(event: SmokingEvent)

    suspend fun delete(event: SmokingEvent)

    suspend fun getSmokingEventsByDate(date: String): List<SmokingEvent>

    suspend fun getLastSmokingEvent(): SmokingEvent?

    suspend fun getSmokingEventsBetween(startDate: String, endDate: String): List<SmokingEvent>
}