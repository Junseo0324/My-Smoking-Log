package com.devhjs.mysmokinglog.domain.usecase

import com.devhjs.mysmokinglog.core.util.Result
import com.devhjs.mysmokinglog.domain.model.UserSetting
import com.devhjs.mysmokinglog.domain.repository.UserSettingRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetSettingsUseCaseTest {

    private lateinit var getSettingsUseCase: GetSettingsUseCase
    private val repository: UserSettingRepository = mockk()

    @Before
    fun `초기화`() {
        getSettingsUseCase = GetSettingsUseCase(repository)
    }

    @Test
    fun `설정을_정상적으로_가져오는지_테스트`() = runTest {
        // Given (준비)
        val expectedSettings = UserSetting(dailyLimit = 20, packPrice = 5000, cigarettesPerPackage = 20)
        coEvery { repository.getSettings() } returns expectedSettings

        // When (실행)
        val result = getSettingsUseCase.execute()

        // Then (검증)
        assert(result is Result.Success)
        assertEquals(expectedSettings, (result as Result.Success).data)
    }

    @Test
    fun `에러_발생_시_에러_결과를_반환하는지_테스트`() = runTest {
        // Given (준비)
        val exception = RuntimeException("DB Error")
        coEvery { repository.getSettings() } throws exception

        // When (실행)
        val result = getSettingsUseCase.execute()

        // Then (검증)
        assert(result is Result.Error)
        assertEquals(exception, (result as Result.Error).error)
    }
}
