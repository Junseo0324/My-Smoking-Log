package com.devhjs.mysmokinglog.domain.repository

import com.devhjs.mysmokinglog.domain.model.Smoking

interface SmokingRepository {
    suspend fun insert(event: Smoking)

    suspend fun delete(event: Smoking)

    suspend fun getSmokingEventsByDate(date: String): List<Smoking>

    suspend fun getLastSmokingEvent(): Smoking

    suspend fun getSmokingEventsBetween(startDate: String, endDate: String): List<Smoking>
}