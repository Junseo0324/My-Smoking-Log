package com.devhjs.mysmokinglog.domain.repository

import com.devhjs.mysmokinglog.domain.model.HealthMilestone

interface HealthMilestoneRepository {
    fun getMilestones(): List<HealthMilestone>
}
