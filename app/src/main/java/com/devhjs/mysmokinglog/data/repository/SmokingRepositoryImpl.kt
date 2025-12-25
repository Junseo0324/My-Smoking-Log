package com.devhjs.mysmokinglog.data.repository

import com.devhjs.mysmokinglog.data.dao.SmokingDao
import com.devhjs.mysmokinglog.data.mapper.toEntity
import com.devhjs.mysmokinglog.data.mapper.toModel
import com.devhjs.mysmokinglog.domain.model.Smoking
import com.devhjs.mysmokinglog.domain.repository.SmokingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SmokingRepositoryImpl @Inject constructor(
    private val smokingDao: SmokingDao
) : SmokingRepository {

    override suspend fun insert(event: Smoking) {
        smokingDao.insert(event.toEntity())
    }

    override suspend fun delete() {
        smokingDao.deleteLastEvent()
    }

    override fun getSmokingEventsByDate(date: String): Flow<List<Smoking>> {
        return smokingDao.getEventsByDate(date).map { entities ->
            entities.map { it.toModel() }
        }
    }

    override fun getLastSmokingEvent(): Flow<Smoking?> {
        return smokingDao.getLastEvent().map { it?.toModel() }
    }

    override suspend fun getSmokingEventsByDateList(date: String): List<Smoking> {
        return smokingDao.getEventsByDateList(date).map { it.toModel() }
    }

    override suspend fun getLastSmokingEventItem(): Smoking? {
        return smokingDao.getLastEventItem()?.toModel()
    }



    override fun getSmokingEventsBetweenFlow(startDate: String, endDate: String): Flow<List<Smoking>> {
        return smokingDao.getEventsBetweenFlow(startDate, endDate).map { entities ->
            entities.map { it.toModel() }
        }
    }
}
