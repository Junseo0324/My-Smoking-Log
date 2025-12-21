package com.devhjs.mysmokinglog.domain.usecase

import com.devhjs.mysmokinglog.domain.model.Smoking
import com.devhjs.mysmokinglog.domain.repository.SmokingRepository
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject

import com.devhjs.mysmokinglog.core.util.Result

class AddSmokingUseCase @Inject constructor(
    private val smokingRepository: SmokingRepository,
    private val clock: Clock
) {
    suspend fun execute(): Result<Unit, Throwable> {
        return try {
            val now = LocalDate.now(clock)
            val timestamp = clock.millis()
            
            smokingRepository.insert(
                Smoking(
                    timestamp = timestamp,
                    date = now.toString()
                )
            )
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
