package com.devhjs.mysmokinglog.domain.usecase

import com.devhjs.mysmokinglog.domain.model.Smoking
import com.devhjs.mysmokinglog.domain.model.UserSetting
import com.devhjs.mysmokinglog.domain.repository.SmokingRepository
import com.devhjs.mysmokinglog.domain.repository.UserSettingRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class GetTodaySmokingWidgetInfoUseCaseTest {

    private lateinit var getTodaySmokingWidgetInfoUseCase: GetTodaySmokingWidgetInfoUseCase
    private val smokingRepository: SmokingRepository = mockk()
    private val userSettingRepository: UserSettingRepository = mockk()

    @Test
    fun `위젯_요구_정보를_제대로_반환하는지_테스트`() = runTest {
        // Given
        val fixedTimeMillis = 1672531200000L
        val fixedDateStr = LocalDate.of(2023, 1, 1).toString()
        val zoneId = ZoneId.of("UTC")
        val fixedClock = Clock.fixed(Instant.ofEpochMilli(fixedTimeMillis), zoneId)

        getTodaySmokingWidgetInfoUseCase = GetTodaySmokingWidgetInfoUseCase(
            smokingRepository = smokingRepository,
            userSettingRepository = userSettingRepository,
            clock = fixedClock
        )

        val mockEvents = listOf(
            Smoking(timestamp = 1672530000000L, date = fixedDateStr),
            Smoking(timestamp = 1672531000000L, date = fixedDateStr)
        )
        val mockLastEvent = mockEvents.last()
        val mockSettings = UserSetting(dailyLimit = 10, packPrice = 4500, cigarettesPerPackage = 20)

        coEvery { smokingRepository.getSmokingEventsByDateList(fixedDateStr) } returns mockEvents
        coEvery { smokingRepository.getLastSmokingEventItem() } returns mockLastEvent
        coEvery { userSettingRepository.getSettings() } returns mockSettings

        // When
        val result = getTodaySmokingWidgetInfoUseCase.execute()

        // Then
        assertEquals(mockEvents.size, result.count)
        assertEquals(mockSettings.dailyLimit, result.dailyLimit)
        assertEquals(mockLastEvent.timestamp, result.lastSmokingTimestamp)
    }

    @Test
    fun `기록이_없을_경우_빈_정보를_제대로_반환하는지_테스트`() = runTest {
        // Given
        val fixedTimeMillis = 1672531200000L // 2023-01-01 00:00:00 UTC
        val fixedDateStr = LocalDate.of(2023, 1, 1).toString()
        val zoneId = ZoneId.of("UTC")
        val fixedClock = Clock.fixed(Instant.ofEpochMilli(fixedTimeMillis), zoneId)

        getTodaySmokingWidgetInfoUseCase = GetTodaySmokingWidgetInfoUseCase(
            smokingRepository = smokingRepository,
            userSettingRepository = userSettingRepository,
            clock = fixedClock
        )

        coEvery { smokingRepository.getSmokingEventsByDateList(fixedDateStr) } returns emptyList()
        coEvery { smokingRepository.getLastSmokingEventItem() } returns null
        coEvery { userSettingRepository.getSettings() } returns UserSetting(dailyLimit = 5, packPrice = 4500, cigarettesPerPackage = 20)

        // When
        val result = getTodaySmokingWidgetInfoUseCase.execute()

        // Then
        assertEquals(0, result.count)
        assertEquals(5, result.dailyLimit)
        assertNull(result.lastSmokingTimestamp)
    }
}
