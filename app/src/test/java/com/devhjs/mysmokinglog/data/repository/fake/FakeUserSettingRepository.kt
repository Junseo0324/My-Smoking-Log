package com.devhjs.mysmokinglog.data.repository.fake

import com.devhjs.mysmokinglog.domain.model.UserSetting
import com.devhjs.mysmokinglog.domain.repository.UserSettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

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
