package com.devhjs.mysmokinglog.domain.repository

import com.devhjs.mysmokinglog.domain.model.Smoking
import kotlinx.coroutines.flow.Flow

interface SmokingRepository {
    suspend fun insert(event: Smoking)

    suspend fun delete()

    fun getSmokingEventsByDate(date: String): Flow<List<Smoking>>

    fun getLastSmokingEvent(): Flow<Smoking?>



    fun getSmokingEventsBetweenFlow(startDate: String, endDate: String): Flow<List<Smoking>>
}