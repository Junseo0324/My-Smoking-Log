package com.devhjs.mysmokinglog.domain.repository

import com.devhjs.mysmokinglog.domain.model.UserSetting
import kotlinx.coroutines.flow.Flow

interface UserSettingRepository {
    suspend fun getSettings(): UserSetting

    fun getSettingsFlow(): Flow<UserSetting>

    suspend fun saveSettings(settings: UserSetting)
}