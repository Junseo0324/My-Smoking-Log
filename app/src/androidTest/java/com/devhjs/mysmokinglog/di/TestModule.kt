package com.devhjs.mysmokinglog.di

import com.devhjs.mysmokinglog.core.di.RepositoryModule
import com.devhjs.mysmokinglog.data.repository.fake.FakeSmokingRepository
import com.devhjs.mysmokinglog.data.repository.fake.FakeUserSettingRepository
import com.devhjs.mysmokinglog.domain.repository.SmokingRepository
import com.devhjs.mysmokinglog.domain.repository.UserSettingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
object TestModule {

    @Provides
    @Singleton
    fun provideSmokingRepository(): SmokingRepository {
        return FakeSmokingRepository()
    }

    @Provides
    @Singleton
    fun provideUserSettingRepository(): UserSettingRepository {
        return FakeUserSettingRepository()
    }
}
