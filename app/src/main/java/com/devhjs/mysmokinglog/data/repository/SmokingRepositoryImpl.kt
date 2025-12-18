package com.devhjs.mysmokinglog.data.repository

import com.devhjs.mysmokinglog.data.datasource.SmokingLocalDataSource
import com.devhjs.mysmokinglog.data.entity.SmokingEventEntity
import com.devhjs.mysmokinglog.domain.model.SmokingEvent
import com.devhjs.mysmokinglog.domain.repository.SmokingRepository
import javax.inject.Inject

class SmokingRepositoryImpl @Inject constructor(
    private val smokingLocalDataSource: SmokingLocalDataSource
) : SmokingRepository {

    override suspend fun insert(event: SmokingEvent) {
        smokingLocalDataSource.insertEvent(event.toEntity())
    }

    override suspend fun delete(event: SmokingEvent) {
        smokingLocalDataSource.deleteEvent(event.toEntity())
    }

    override suspend fun getSmokingEventsByDate(date: String): List<SmokingEvent> {
        return smokingLocalDataSource.getEventsByDate(date).map { it.toDomain() }
    }

    override suspend fun getLastSmokingEvent(): SmokingEvent? {
        return smokingLocalDataSource.getLastEvent()?.toDomain()
    }

    override suspend fun getSmokingEventsBetween(startDate: String, endDate: String): List<SmokingEvent> {
        return smokingLocalDataSource.getEventsBetween(startDate, endDate).map { it.toDomain() }
    }

    private fun SmokingEvent.toEntity(): SmokingEventEntity {
        return SmokingEventEntity(
            id = id,
            timestamp = timestamp,
            date = date
        )
    }

    private fun SmokingEventEntity.toDomain(): SmokingEvent {
        return SmokingEvent(
            id = id,
            timestamp = timestamp,
            date = date
        )
    }
}
