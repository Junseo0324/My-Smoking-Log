package com.devhjs.mysmokinglog.core.di

import com.devhjs.mysmokinglog.data.dao.SmokingEventDao
import com.devhjs.mysmokinglog.data.dao.UserSettingsDao
import com.devhjs.mysmokinglog.data.datasource.SmokingLocalDataSource
import com.devhjs.mysmokinglog.data.datasource.SmokingLocalDataSourceImpl
import com.devhjs.mysmokinglog.data.datasource.UserSettingsLocalDataSource
import com.devhjs.mysmokinglog.data.datasource.UserSettingsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideSmokingLocalDataSource(
        smokingEventDao: SmokingEventDao
    ): SmokingLocalDataSource {
        return SmokingLocalDataSourceImpl(
            smokingEventDao = smokingEventDao
        )
    }

    @Provides
    @Singleton
    fun provideUserSettingsLocalDataSource(
        userSettingsDao: UserSettingsDao
    ): UserSettingsLocalDataSource {
        return UserSettingsLocalDataSourceImpl(
            userSettingsDao = userSettingsDao
        )
    }
}