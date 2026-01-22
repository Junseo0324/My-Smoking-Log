package com.devhjs.mysmokinglog.data.repository

import android.content.Context
import com.devhjs.mysmokinglog.R
import com.devhjs.mysmokinglog.domain.model.HealthMilestone
import com.devhjs.mysmokinglog.domain.repository.HealthMilestoneRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StaticHealthMilestoneRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : HealthMilestoneRepository {

    override fun getMilestones(): List<HealthMilestone> {
        return listOf(
            HealthMilestone(20, context.getString(R.string.health_20m_title), context.getString(R.string.health_20m_desc)),
            HealthMilestone(12 * 60, context.getString(R.string.health_12h_title), context.getString(R.string.health_12h_desc)),
            HealthMilestone(2 * 7 * 24 * 60, context.getString(R.string.health_2w_title), context.getString(R.string.health_2w_desc)),
            HealthMilestone(1 * 30 * 24 * 60, context.getString(R.string.health_1m_title), context.getString(R.string.health_1m_desc)),
            HealthMilestone(1 * 365 * 24 * 60, context.getString(R.string.health_1y_title), context.getString(R.string.health_1y_desc)),
            HealthMilestone(5 * 365 * 24 * 60, context.getString(R.string.health_5y_title), context.getString(R.string.health_5y_desc)),
            HealthMilestone(10 * 365 * 24 * 60, context.getString(R.string.health_10y_title), context.getString(R.string.health_10y_desc)),
            HealthMilestone(15 * 365 * 24 * 60, context.getString(R.string.health_15y_title), context.getString(R.string.health_15y_desc))
        )
    }
}
