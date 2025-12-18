package com.devhjs.mysmokinglog.data.datasource

import com.devhjs.mysmokinglog.data.entity.UserSettingsEntity

interface UserSettingsLocalDataSource {
    suspend fun getSettings(): UserSettingsEntity?

    suspend fun saveSettings(settings: UserSettingsEntity)
}