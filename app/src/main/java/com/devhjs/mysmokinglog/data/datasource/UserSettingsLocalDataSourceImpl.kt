package com.devhjs.mysmokinglog.data.datasource

import com.devhjs.mysmokinglog.data.dao.UserSettingsDao
import com.devhjs.mysmokinglog.data.entity.UserSettingsEntity

class UserSettingsLocalDataSourceImpl(
    private val userSettingsDao: UserSettingsDao
) : UserSettingsLocalDataSource {

    override suspend fun getSettings(): UserSettingsEntity? {
        return userSettingsDao.getSettings()
    }

    override suspend fun saveSettings(settings: UserSettingsEntity) {
        userSettingsDao.saveSettings(settings)
    }
}
