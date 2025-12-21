package com.devhjs.mysmokinglog.domain.usecase

import com.devhjs.mysmokinglog.core.util.Result
import com.devhjs.mysmokinglog.domain.repository.SmokingRepository
import javax.inject.Inject

class DeleteSmokingUseCase @Inject constructor(
    private val smokingRepository: SmokingRepository,
)  {
    suspend fun execute(): Result<Unit, Throwable> {
        return try {
            smokingRepository.delete()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }

    }
}