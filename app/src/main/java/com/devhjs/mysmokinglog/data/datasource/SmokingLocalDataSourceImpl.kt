package com.devhjs.mysmokinglog.data.datasource

import com.devhjs.mysmokinglog.data.dao.SmokingEventDao
import com.devhjs.mysmokinglog.data.entity.SmokingEventEntity

class SmokingLocalDataSourceImpl(
    private val smokingEventDao: SmokingEventDao
) : SmokingLocalDataSource {
    override suspend fun insertEvent(event: SmokingEventEntity) {
        smokingEventDao.insert(event)
    }

    override suspend fun deleteEvent(event: SmokingEventEntity) {
        smokingEventDao.delete(event)
    }

    override suspend fun getEventsByDate(date: String): List<SmokingEventEntity> {
        return smokingEventDao.getEventsByDate(date)
    }

    override suspend fun getLastEvent(): SmokingEventEntity {
        return smokingEventDao.getLastEvent()
    }

    override suspend fun getEventsBetween(
        startDate: String,
        endDate: String
    ): List<SmokingEventEntity> {
        return smokingEventDao.getEventsBetween(startDate, endDate)
    }
}