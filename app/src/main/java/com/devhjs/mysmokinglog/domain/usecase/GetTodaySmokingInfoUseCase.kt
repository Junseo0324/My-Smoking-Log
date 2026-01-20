package com.devhjs.mysmokinglog.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.devhjs.mysmokinglog.core.util.Result

import com.devhjs.mysmokinglog.domain.model.TodaySmoking
import com.devhjs.mysmokinglog.domain.repository.SmokingRepository
import com.devhjs.mysmokinglog.domain.repository.UserSettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class GetTodaySmokingInfoUseCase @Inject constructor(
    private val smokingRepository: SmokingRepository,
    private val userSettingRepository: UserSettingRepository,
    private val clock: Clock
) {
    fun execute(): Flow<Result<TodaySmoking, Throwable>> {
        val today = LocalDate.now(clock).toString()
        
        return combine(
            smokingRepository.getSmokingEventsByDate(today),
            smokingRepository.getLastSmokingEvent()
        ) { events, lastEvent ->
            try {
                val dailyLimit = userSettingRepository.getSettings().dailyLimit
                val count = events.size
                val lastSmokingTimestamp = lastEvent?.timestamp
                
                Result.Success(
                    TodaySmoking(
                        count = count,
                        dailyLimit = dailyLimit,
                        lastSmokingTimestamp = lastSmokingTimestamp
                    )
                )
            } catch (e: Throwable) {
                Result.Error(e)
            }
        }.catch { e ->
            emit(Result.Error(e))
        }
    }
}