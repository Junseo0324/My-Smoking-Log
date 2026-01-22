package com.devhjs.mysmokinglog.presentation.health

import com.devhjs.mysmokinglog.core.util.UiText
import com.devhjs.mysmokinglog.domain.model.HealthMilestone

data class HealthTimelineState(
    val milestones: List<HealthMilestone> = emptyList(),
    val timeSinceLastSmoking: UiText = UiText.DynamicString("")
)
