package com.devhjs.mysmokinglog.core.di

import com.devhjs.mysmokinglog.data.repository.SmokingRepositoryImpl
import com.devhjs.mysmokinglog.data.repository.StaticHealthMilestoneRepositoryImpl
import com.devhjs.mysmokinglog.data.repository.UserSettingRepositoryImpl
import com.devhjs.mysmokinglog.domain.repository.HealthMilestoneRepository
import com.devhjs.mysmokinglog.domain.repository.SmokingRepository
import com.devhjs.mysmokinglog.domain.repository.UserSettingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserSettingRepository(
        impl: UserSettingRepositoryImpl
    ): UserSettingRepository

    @Binds
    abstract fun bindSmokingRepository(
        impl: SmokingRepositoryImpl
    ): SmokingRepository

    @Binds
    abstract fun bindHealthMilestoneRepository(
        impl: StaticHealthMilestoneRepositoryImpl
    ): HealthMilestoneRepository
}