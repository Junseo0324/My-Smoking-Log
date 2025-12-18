package com.devhjs.mysmokinglog.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devhjs.mysmokinglog.data.entity.UserSettingEntity

@Dao
interface UserSettingsDao {

    // 설정 조회
    @Query("SELECT * FROM user_settings LIMIT 1")
    suspend fun getSettings(): UserSettingEntity

    // 설정 저장
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSettings(settings: UserSettingEntity)
}