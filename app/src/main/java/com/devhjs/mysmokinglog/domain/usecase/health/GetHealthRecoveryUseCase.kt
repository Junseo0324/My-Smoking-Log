package com.devhjs.mysmokinglog.domain.usecase.health

import com.devhjs.mysmokinglog.domain.model.HealthMilestone
import com.devhjs.mysmokinglog.domain.repository.HealthMilestoneRepository
import com.devhjs.mysmokinglog.domain.repository.SmokingRepository
import javax.inject.Inject

class GetHealthRecoveryUseCase @Inject constructor(
    private val smokingRepository: SmokingRepository,
    private val healthMilestoneRepository: HealthMilestoneRepository
) {
    suspend operator fun invoke(): List<HealthMilestone> {
        val lastEvent = smokingRepository.getLastSmokingEventItem()
        val lastSmokingTime = lastEvent?.timestamp ?: System.currentTimeMillis()
        val timeSinceLastSmoking = (System.currentTimeMillis() - lastSmokingTime) / 1000 / 60

        return healthMilestoneRepository.getMilestones().map { milestone ->
            milestone.copy(
                isAchieved = timeSinceLastSmoking >= milestone.minutesRequired
            )
        }
    }
}
