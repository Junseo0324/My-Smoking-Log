package com.devhjs.mysmokinglog.domain.usecase

import com.devhjs.mysmokinglog.domain.model.Smoking
import com.devhjs.mysmokinglog.domain.repository.SmokingRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.Clock
import java.time.LocalDate
import java.time.ZoneId
import com.devhjs.mysmokinglog.core.util.Result

class AddSmokingUseCaseTest {

    private lateinit var addSmokingUseCase: AddSmokingUseCase
    private val smokingRepository: SmokingRepository = mockk()
    private val clock: Clock = mockk()

    @Before
    fun `초기화`() {
        addSmokingUseCase = AddSmokingUseCase(smokingRepository, clock)
    }



    @Test
    fun `실행_시_올바른_날짜와_시간으로_저장이_호출되는지_테스트`() = runTest {
        // Given (준비)
        val fixedTimeMillis = 1672531200000L // 2023-01-01 00:00:00 UTC
        val fixedDate = LocalDate.of(2023, 1, 1)
        val zoneId = ZoneId.of("UTC")
        
        // Mock Clock (시간 설정)
        // Clock.fixed를 사용하여 고정된 시간을 반환하도록 설정
        val fixedClock = Clock.fixed(java.time.Instant.ofEpochMilli(fixedTimeMillis), zoneId)
        addSmokingUseCase = AddSmokingUseCase(smokingRepository, fixedClock)

        coEvery { smokingRepository.insert(any()) } just Runs

        // When (실행)
        val result = addSmokingUseCase.execute()

        // Then (검증)
        coVerify {
            smokingRepository.insert(
                Smoking(
                    timestamp = fixedTimeMillis,
                    date = fixedDate.toString()
                )
            )
        }
        assert(result is Result.Success)
    }

    @Test
    fun `저장_실패_시_에러_결과를_반환하는지_테스트`() = runTest {
        // Given
        val fixedTimeMillis = 1672531200000L
        val zoneId = ZoneId.of("UTC")
        val fixedClock = Clock.fixed(java.time.Instant.ofEpochMilli(fixedTimeMillis), zoneId)
        addSmokingUseCase = AddSmokingUseCase(smokingRepository, fixedClock)
        
        val exception = RuntimeException("DB Error")
        coEvery { smokingRepository.insert(any()) } throws exception

        // When
        val result = addSmokingUseCase.execute()

        // Then
        assert(result is Result.Error)
        assertEquals(exception, (result as Result.Error).error)
    }
}
