package com.devhjs.mysmokinglog.domain.usecase


import android.os.Build
import androidx.annotation.RequiresApi
import com.devhjs.mysmokinglog.domain.model.TodaySmoking
import com.devhjs.mysmokinglog.domain.repository.SmokingRepository
import com.devhjs.mysmokinglog.domain.repository.UserSettingRepository
// import kotlinx.coroutines.flow.first
import java.time.Clock
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class GetTodaySmokingWidgetInfoUseCase(
    private val smokingRepository: SmokingRepository,
    private val userSettingRepository: UserSettingRepository,
    private val clock: Clock
) {

    suspend fun execute(): TodaySmoking {
        val today = LocalDate.now(clock).toString()

        val events = smokingRepository
            .getSmokingEventsByDateList(today)

        val lastEvent = smokingRepository
            .getLastSmokingEventItem()

        val dailyLimit = userSettingRepository.getSettings().dailyLimit

        return TodaySmoking(
            count = events.size,
            dailyLimit = dailyLimit,
            lastSmokingTimestamp = lastEvent?.timestamp
        )
    }
}
