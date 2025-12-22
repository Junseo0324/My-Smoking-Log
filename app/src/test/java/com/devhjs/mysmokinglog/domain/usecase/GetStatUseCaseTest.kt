package com.devhjs.mysmokinglog.domain.usecase

import com.devhjs.mysmokinglog.domain.model.Smoking
import com.devhjs.mysmokinglog.domain.model.UserSetting
import com.devhjs.mysmokinglog.domain.repository.SmokingRepository
import com.devhjs.mysmokinglog.domain.repository.UserSettingRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneId

class GetStatUseCaseTest {

    private lateinit var getStatUseCase: GetStatUseCase
    private val smokingRepository: SmokingRepository = mockk()
    private val userSettingRepository: UserSettingRepository = mockk()

    @Before
    fun setup() {
        getStatUseCase = GetStatUseCase(smokingRepository, userSettingRepository)
    }

    @Test
    fun `통계 계산이 정확해야 한다`() = runTest {
        // Given
        val now = LocalDateTime.now()
        val todayStart = now.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val yesterdayStart = now.minusDays(1).toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

        val smokingEvents = listOf(
            Smoking(timestamp = todayStart + 3600000, date = "2024-01-01"), // 오늘 01:00
            Smoking(timestamp = todayStart + 7200000, date = "2024-01-01"), // 오늘 02:00 (1시간 차이)
            Smoking(timestamp = yesterdayStart + 3600000, date = "2024-01-01") // 어제 01:00
        )

        every { smokingRepository.getSmokingEventsBetweenFlow(any(), any()) } returns flowOf(smokingEvents)
        every { userSettingRepository.getSettingsFlow() } returns flowOf(UserSetting(dailyLimit = 10, packPrice = 4500, cigarettesPerPackage = 20))

        // When
        val stats = getStatUseCase().first()

        // Then
        assertEquals(3, stats.cigarettesTotalCount)
        // 비용: 3개 * (4500 / 20) = 3 * 225 = 675
        assertEquals(675, stats.thisMonthCost)
        
        // 오늘 개수: 2개 (오늘 01:00, 02:00)
        assertEquals(2, stats.todayTime.sum())
        
        assertEquals(24, stats.longestStreak)
        
        assertEquals(0, stats.streak)
    }

    @Test
    fun `데이터가 없을 때 통계는 0이어야 한다`() = runTest {
        // Given
        every { smokingRepository.getSmokingEventsBetweenFlow(any(), any()) } returns flowOf(emptyList())
        every { userSettingRepository.getSettingsFlow() } returns flowOf(UserSetting(dailyLimit = 10, packPrice = 4500, cigarettesPerPackage = 20))

        // When
        val stats = getStatUseCase().first()

        // Then
        assertEquals(0, stats.cigarettesTotalCount)
        assertEquals(0, stats.thisMonthCost)
        assertEquals(0, stats.longestStreak)
        assertEquals(0, stats.streak)
        assertEquals(0, stats.todayTime.sum())
    }
}
