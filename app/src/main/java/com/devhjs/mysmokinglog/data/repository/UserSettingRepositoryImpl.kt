package com.devhjs.mysmokinglog.data.repository

import com.devhjs.mysmokinglog.data.dao.UserSettingsDao
import com.devhjs.mysmokinglog.data.mapper.toEntity
import com.devhjs.mysmokinglog.data.mapper.toModel
import com.devhjs.mysmokinglog.domain.model.UserSetting
import com.devhjs.mysmokinglog.domain.repository.UserSettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserSettingRepositoryImpl @Inject constructor(
    private val userSettingsDao: UserSettingsDao
) : UserSettingRepository {
    override suspend fun getSettings(): UserSetting {
        val setting = userSettingsDao.getSettings()

        return if (setting == null) {
            val default = UserSetting.default()
            userSettingsDao.saveSettings(default.toEntity())
            default
        } else {
            setting.toModel()
        }
    }

    override suspend fun saveSettings(settings: UserSetting) {
        userSettingsDao.saveSettings(settings.toEntity())
    }

    override fun getSettingsFlow(): Flow<UserSetting> {
        return userSettingsDao.getSettingsFlow().map { entity ->
            entity?.toModel() ?: UserSetting.default()
        }
    }

}