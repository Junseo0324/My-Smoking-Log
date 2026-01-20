package com.devhjs.mysmokinglog.domain.usecase

import com.devhjs.mysmokinglog.core.util.Result
import com.devhjs.mysmokinglog.domain.model.UserSetting
import com.devhjs.mysmokinglog.domain.repository.UserSettingRepository
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val repository: UserSettingRepository
) {
    suspend fun execute(): Result<UserSetting, Throwable> {
        return try {
            val settings = repository.getSettings()
            Result.Success(settings)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
