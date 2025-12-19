package com.devhjs.mysmokinglog.data.repository

import com.devhjs.mysmokinglog.data.dao.SmokingDao
import com.devhjs.mysmokinglog.data.mapper.toEntity
import com.devhjs.mysmokinglog.data.mapper.toModel
import com.devhjs.mysmokinglog.domain.model.Smoking
import com.devhjs.mysmokinglog.domain.repository.SmokingRepository
import javax.inject.Inject

class SmokingRepositoryImpl @Inject constructor(
    private val smokingDao: SmokingDao
) : SmokingRepository {

    override suspend fun insert(event: Smoking) {
        smokingDao.insert(event.toEntity())
    }

    override suspend fun delete(event: Smoking) {
        smokingDao.delete(event.toEntity())
    }

    override suspend fun getSmokingEventsByDate(date: String): List<Smoking> {
        return smokingDao.getEventsByDate(date).map { it.toModel() }
    }

    override suspend fun getLastSmokingEvent(): Smoking {
        return smokingDao.getLastEvent().toModel()
    }

    override suspend fun getSmokingEventsBetween(startDate: String, endDate: String): List<Smoking> {
        return smokingDao.getEventsBetween(startDate, endDate).map { it.toModel() }
    }
}
