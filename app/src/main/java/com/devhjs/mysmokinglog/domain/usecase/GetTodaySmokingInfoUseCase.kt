package com.devhjs.mysmokinglog.domain.usecase

import android.util.Log
import com.devhjs.mysmokinglog.core.util.formatTimeAgo
import com.devhjs.mysmokinglog.domain.model.TodaySmoking
import com.devhjs.mysmokinglog.domain.repository.SmokingRepository
import com.devhjs.mysmokinglog.domain.repository.UserSettingRepository
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject

class GetTodaySmokingInfoUseCase @Inject constructor(
    private val smokingRepository: SmokingRepository,
    private val userSettingRepository: UserSettingRepository,
    private val clock: Clock
) {
    suspend fun execute(): TodaySmoking {
        return try {
            val today = LocalDate.now(clock).toString()
            val todayCount = smokingRepository.getSmokingEventsByDate(today).size
            val dailyLimit = userSettingRepository.getSettings().dailyLimit
            val lastSmoking = smokingRepository.getLastSmokingEvent().timestamp

            TodaySmoking(
                count = todayCount,
                dailyLimit = dailyLimit,
                lastSmokingTime = formatTimeAgo(lastSmoking,clock)
            )
        } catch (e: Exception) {
            Log.d("TAG", "execute: ${e.message}")
            TodaySmoking(
                count = 0,
                dailyLimit = 0,
                lastSmokingTime = "알 수 없음"
            )
        }
    }
}