package com.devhjs.mysmokinglog.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devhjs.mysmokinglog.data.dao.SmokingDao
import com.devhjs.mysmokinglog.data.dao.UserSettingsDao
import com.devhjs.mysmokinglog.data.entity.SmokingEntity
import com.devhjs.mysmokinglog.data.entity.UserSettingEntity

@Database(
    entities = [
        SmokingEntity::class,
        UserSettingEntity::class
    ],
    version = 1,
    exportSchema = true
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun smokingDao(): SmokingDao

    abstract fun userSettingsDao(): UserSettingsDao
}