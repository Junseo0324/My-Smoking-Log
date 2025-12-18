package com.devhjs.mysmokinglog.domain.repository

interface UserSettingRepository {
    suspend fun getSettings()

    suspend fun saveSettings()
}