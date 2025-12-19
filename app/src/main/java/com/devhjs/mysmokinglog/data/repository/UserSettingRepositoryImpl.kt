package com.devhjs.mysmokinglog.data.repository

import com.devhjs.mysmokinglog.data.dao.UserSettingsDao
import com.devhjs.mysmokinglog.data.mapper.toEntity
import com.devhjs.mysmokinglog.data.mapper.toModel
import com.devhjs.mysmokinglog.domain.model.UserSetting
import com.devhjs.mysmokinglog.domain.repository.UserSettingRepository
import javax.inject.Inject

class UserSettingRepositoryImpl @Inject constructor(
    private val userSettingsDao: UserSettingsDao
) : UserSettingRepository {
    override suspend fun getSettings(): UserSetting {
        return userSettingsDao.getSettings().toModel()
    }

    override suspend fun saveSettings(settings: UserSetting) {
        userSettingsDao.saveSettings(settings.toEntity())
    }

}