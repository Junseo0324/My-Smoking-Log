package com.devhjs.mysmokinglog.domain.usecase

import com.devhjs.mysmokinglog.core.util.Result
import com.devhjs.mysmokinglog.domain.model.UserSetting
import com.devhjs.mysmokinglog.domain.repository.UserSettingRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SaveSettingsUseCaseTest {

    private lateinit var saveSettingsUseCase: SaveSettingsUseCase
    private val repository: UserSettingRepository = mockk()

    @Before
    fun `초기화`() {
        saveSettingsUseCase = SaveSettingsUseCase(repository)
    }

    @Test
    fun `설정을_정상적으로_저장하는지_테스트`() = runTest {
        // Given (준비)
        val newSettings = UserSetting(dailyLimit = 15, packPrice = 5500, cigarettesPerPackage = 20)
        coEvery { repository.saveSettings(newSettings) } returns Unit

        // When (실행)
        val result = saveSettingsUseCase.execute(newSettings)

        // Then (검증)
        assert(result is Result.Success)
        coVerify { repository.saveSettings(newSettings) }
    }

    @Test
    fun `에러_발생_시_에러_결과를_반환하는지_테스트`() = runTest {
        // Given (준비)
        val newSettings = UserSetting(dailyLimit = 15, packPrice = 5500, cigarettesPerPackage = 20)
        val exception = RuntimeException("Save Error")
        coEvery { repository.saveSettings(any()) } throws exception

        // When (실행)
        val result = saveSettingsUseCase.execute(newSettings)

        // Then (검증)
        assert(result is Result.Error)
        assertEquals(exception, (result as Result.Error).error)
    }
}
