package com.devhjs.mysmokinglog.domain.usecase

import com.devhjs.mysmokinglog.core.util.Result
import com.devhjs.mysmokinglog.domain.model.Smoking
import com.devhjs.mysmokinglog.domain.model.UserSetting
import com.devhjs.mysmokinglog.domain.repository.SmokingRepository
import com.devhjs.mysmokinglog.domain.repository.UserSettingRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.Clock
import java.time.Instant.ofEpochMilli
import java.time.ZoneId

class GetTodaySmokingInfoUseCaseTest {

    private lateinit var getTodaySmokingInfoUseCase: GetTodaySmokingInfoUseCase
    private val smokingRepository: SmokingRepository = mockk()
    private val userSettingRepository: UserSettingRepository = mockk()
    private val clock: Clock = mockk()

    @Before
    fun `초기화`() {
        getTodaySmokingInfoUseCase = GetTodaySmokingInfoUseCase(
            smokingRepository,
            userSettingRepository,
            clock
        )
    }



    @Test
    fun `오늘의_흡연_정보를_정상적으로_가져오는지_테스트`() = runTest {
        // Given (준비)
        val todayStr = "2023-01-01"
        val fixedMillis = 1672531200000L // 2023-01-01 00:00:00 UTC

        // Mock Clock (시간 설정)
        val fixedClock = Clock.fixed(ofEpochMilli(fixedMillis), ZoneId.of("UTC"))
        getTodaySmokingInfoUseCase = GetTodaySmokingInfoUseCase(smokingRepository, userSettingRepository, fixedClock)

        val smokingEvents = listOf(
            Smoking(timestamp = fixedMillis, date = todayStr),
            Smoking(timestamp = fixedMillis + 1000, date = todayStr)
        )
        val userSettings = UserSetting(dailyLimit = 15, packPrice = 4500, cigarettesPerPackage = 20)
        val lastSmoking = Smoking(timestamp = fixedMillis + 1000, date = todayStr)

        coEvery { smokingRepository.getSmokingEventsByDate(todayStr) } returns smokingEvents
        coEvery { userSettingRepository.getSettings() } returns userSettings
        coEvery { smokingRepository.getLastSmokingEvent() } returns lastSmoking

        // When (실행)
        val result = getTodaySmokingInfoUseCase.execute()

        // Then (검증)
        assert(result is Result.Success)
        val data = (result as Result.Success).data
        assertEquals(2, data.count) // 오늘 2개 핌
        assertEquals(15, data.dailyLimit) // 목표 15개
    }

    @Test
    fun `에러_발생_시_에러_결과를_반환하는지_테스트`() = runTest {
        // Given (준비)
        val fixedMillis = 1672531200000L
        val fixedClock = Clock.fixed(ofEpochMilli(fixedMillis), ZoneId.of("UTC"))
        getTodaySmokingInfoUseCase = GetTodaySmokingInfoUseCase(smokingRepository, userSettingRepository, fixedClock)
        
        val exception = RuntimeException("DB Error")
        coEvery { smokingRepository.getSmokingEventsByDate(any()) } throws exception

        // When (실행)
        val result = getTodaySmokingInfoUseCase.execute()

        // Then (검증)
        assert(result is Result.Error)
        assertEquals(exception, (result as Result.Error).error)
    }
}
