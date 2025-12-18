package com.devhjs.mysmokinglog.data.datasource

import com.devhjs.mysmokinglog.data.dao.UserSettingsDao
import com.devhjs.mysmokinglog.data.entity.UserSettingEntity

class UserSettingsLocalDataSourceImpl(
    private val userSettingsDao: UserSettingsDao
) : UserSettingsLocalDataSource {

    override suspend fun getSettings(): UserSettingEntity {
        return userSettingsDao.getSettings()
    }

    override suspend fun saveSettings(settings: UserSettingEntity) {
        userSettingsDao.saveSettings(settings)
    }
}
