package com.devhjs.mysmokinglog.domain.repository

import com.devhjs.mysmokinglog.domain.model.UserSetting

interface UserSettingRepository {
    suspend fun getSettings(): UserSetting

    suspend fun saveSettings(settings: UserSetting)
}