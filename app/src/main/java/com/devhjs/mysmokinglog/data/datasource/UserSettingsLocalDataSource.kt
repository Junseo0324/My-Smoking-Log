package com.devhjs.mysmokinglog.data.datasource

import com.devhjs.mysmokinglog.data.entity.UserSettingEntity

interface UserSettingsLocalDataSource {
    suspend fun getSettings(): UserSettingEntity

    suspend fun saveSettings(settings: UserSettingEntity)
}