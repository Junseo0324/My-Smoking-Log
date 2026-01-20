package com.devhjs.mysmokinglog.core.di

import android.content.Context
import androidx.room.Room
import com.devhjs.mysmokinglog.data.local.AppDatabase
import com.devhjs.mysmokinglog.data.local.dao.SmokingDao
import com.devhjs.mysmokinglog.data.local.dao.UserSettingsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DB_NAME = "my_smoking_log.db"

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    fun provideSmokingEventDao(db: AppDatabase): SmokingDao {
        return db.smokingDao()
    }

    @Provides
    fun provideUserSettingsDao(db: AppDatabase): UserSettingsDao {
        return db.userSettingsDao()
    }
}