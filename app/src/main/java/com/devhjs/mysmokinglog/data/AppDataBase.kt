package com.devhjs.mysmokinglog.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devhjs.mysmokinglog.data.dao.SmokingEventDao
import com.devhjs.mysmokinglog.data.dao.UserSettingsDao
import com.devhjs.mysmokinglog.data.entity.SmokingEventEntity
import com.devhjs.mysmokinglog.data.entity.UserSettingsEntity

@Database(
    entities = [
        SmokingEventEntity::class,
        UserSettingsEntity::class
    ],
    version = 1,
    exportSchema = true
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun smokingEventDao(): SmokingEventDao

    abstract fun userSettingsDao(): UserSettingsDao
}