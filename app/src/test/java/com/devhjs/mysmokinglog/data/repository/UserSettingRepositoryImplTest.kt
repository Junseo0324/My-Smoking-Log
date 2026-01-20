package com.devhjs.mysmokinglog.data.repository

import com.devhjs.mysmokinglog.data.local.dao.UserSettingsDao
import com.devhjs.mysmokinglog.data.local.entity.UserSettingEntity
import com.devhjs.mysmokinglog.domain.model.UserSetting
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserSettingRepositoryImplTest {

    private lateinit var repository: UserSettingRepositoryImpl
    private val userSettingsDao: UserSettingsDao = mockk()

    @Before
    fun `초기화`() {
        repository = UserSettingRepositoryImpl(userSettingsDao)
    }

    @Test
    fun `설정이_없을_경우_기본값을_생성하고_저장하는지_테스트`() = runTest {
        // Given (준비)
        coEvery { userSettingsDao.getSettings() } returns null
        coEvery { userSettingsDao.saveSettings(any()) } just Runs

        // When (실행)
        val result = repository.getSettings()

        // Then (검증)
        val expectedDefault = UserSetting.default()
        assertEquals(expectedDefault, result)
        
        // 저장이 호출되었는지 확인
        coVerify { userSettingsDao.saveSettings(any()) }
    }

    @Test
    fun `설정이_있을_경우_해당_설정을_반환하는지_테스트`() = runTest {
        // Given (준비)
        val existingEntity = UserSettingEntity(dailyLimit = 10, packPrice = 4500, cigarettesPerPack = 20)
        coEvery { userSettingsDao.getSettings() } returns existingEntity

        // When (실행)
        val result = repository.getSettings()

        // Then (검증)
        assertEquals(10, result.dailyLimit)
    }

    @Test
    fun `설정_저장이_정상적으로_호출되는지_테스트`() = runTest {
        // Given (준비)
        val newSetting = UserSetting(dailyLimit = 5, packPrice = 5000, cigarettesPerPackage = 20)
        val expectedEntity = UserSettingEntity(dailyLimit = 5, packPrice = 5000, cigarettesPerPack = 20)
        coEvery { userSettingsDao.saveSettings(any()) } just Runs

        // When (실행)
        repository.saveSettings(newSetting)

        // Then (검증)
        coVerify { userSettingsDao.saveSettings(expectedEntity) }
    }
}
