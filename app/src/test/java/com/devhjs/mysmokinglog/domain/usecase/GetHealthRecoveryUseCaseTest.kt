package com.devhjs.mysmokinglog.domain.usecase

import com.devhjs.mysmokinglog.domain.model.HealthMilestone
import com.devhjs.mysmokinglog.domain.model.Smoking
import com.devhjs.mysmokinglog.domain.repository.HealthMilestoneRepository
import com.devhjs.mysmokinglog.domain.repository.SmokingRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetHealthRecoveryUseCaseTest {

    private lateinit var getHealthRecoveryUseCase: GetHealthRecoveryUseCase
    private val smokingRepository: SmokingRepository = mockk()
    private val healthMilestoneRepository: HealthMilestoneRepository = mockk()

    @Before
    fun `초기화`() {
        getHealthRecoveryUseCase = GetHealthRecoveryUseCase(
            smokingRepository,
            healthMilestoneRepository
        )
    }

    @Test
    fun `건강회복_정보의_목표달성_여부를_올바르게_반환하는지_테스트`() = runTest {
        // Given
        val timeRequiredMinutes = 120L
        val pastTimestamp = System.currentTimeMillis() - (timeRequiredMinutes * 60 * 1000)
        
        val lastEvent = Smoking(timestamp = pastTimestamp, date = "2023-01-01")
        
        val milestones = listOf(
            HealthMilestone(minutesRequired = 60L, title = "1시간 경과", description = "혈압 정상화"),
            HealthMilestone(minutesRequired = 180L, title = "3시간 경과", description = "산소 수치 증가")
        )

        coEvery { smokingRepository.getLastSmokingEventItem() } returns lastEvent
        coEvery { healthMilestoneRepository.getMilestones() } returns milestones

        // When
        val result = getHealthRecoveryUseCase.invoke()

        // Then
        assertEquals(2, result.size)
        assertTrue(result[0].isAchieved)
        assertFalse(result[1].isAchieved)
    }

    @Test
    fun `흡연기록이_없을_경우_모두_미달성으로_반환하는지_테스트`() = runTest {
        // Given
        val milestones = listOf(
            HealthMilestone(minutesRequired = 60L, title = "1시간 경과", description = "혈압 정상화"),
            HealthMilestone(minutesRequired = 180L, title = "3시간 경과", description = "산소 수치 증가")
        )

        coEvery { smokingRepository.getLastSmokingEventItem() } returns null
        coEvery { healthMilestoneRepository.getMilestones() } returns milestones

        // When
        val result = getHealthRecoveryUseCase.invoke()

        // Then
        assertEquals(2, result.size)
        assertFalse(result[0].isAchieved)
        assertFalse(result[1].isAchieved)
    }
}
