package com.devhjs.mysmokinglog.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        // instance 가 있는지 체크
        fun getInstance(context: Context): AppDatabase {
            // 이미 생성된 인스턴스가 있는지 확인
            return INSTANCE ?: synchronized(this) {
                // 여러 스레드가 동시에 synchronized에 도달했을 경우를 대비해 재확인
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "my_smoking_log.db"
                )
                    .build()
                    // 저장
                    .also { INSTANCE = it }
            }
        }
    }
}