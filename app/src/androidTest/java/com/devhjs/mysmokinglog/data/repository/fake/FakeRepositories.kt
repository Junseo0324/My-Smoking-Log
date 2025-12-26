package com.devhjs.mysmokinglog.data.repository.fake

import com.devhjs.mysmokinglog.domain.model.Smoking
import com.devhjs.mysmokinglog.domain.model.UserSetting
import com.devhjs.mysmokinglog.domain.repository.SmokingRepository
import com.devhjs.mysmokinglog.domain.repository.UserSettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeSmokingRepository : SmokingRepository {

    private val _events = MutableStateFlow<List<Smoking>>(emptyList())

    override suspend fun insert(event: Smoking) {
        _events.update { it + event }
    }

    override suspend fun delete() {
        _events.update { list ->
            if (list.isNotEmpty()) {
                list.sortedBy { it.timestamp }.dropLast(1)
            } else {
                list
            }
        }
    }

    override fun getSmokingEventsByDate(date: String): Flow<List<Smoking>> {
        return _events.map { list ->
            list.filter { it.date == date }
        }
    }

    override fun getLastSmokingEvent(): Flow<Smoking?> {
        return _events.map { list ->
            list.maxByOrNull { it.timestamp }
        }
    }

    override suspend fun getSmokingEventsByDateList(date: String): List<Smoking> {
        return _events.value.filter { it.date == date }
    }

    override suspend fun getLastSmokingEventItem(): Smoking? {
        return _events.value.maxByOrNull { it.timestamp }
    }

    override fun getSmokingEventsBetweenFlow(startDate: String, endDate: String): Flow<List<Smoking>> {
        return _events.map { list ->
            list.filter {
                it.date >= startDate && it.date <= endDate
            }
        }
    }
}

class FakeUserSettingRepository : UserSettingRepository {

    private val _settings = MutableStateFlow(
        UserSetting(
            dailyLimit = 20,
            packPrice = 4500,
            cigarettesPerPackage = 20
        )
    )

    override suspend fun getSettings(): UserSetting {
        return _settings.value
    }

    override fun getSettingsFlow(): Flow<UserSetting> {
        return _settings.asStateFlow()
    }

    override suspend fun saveSettings(settings: UserSetting) {
        _settings.value = settings
    }
}
