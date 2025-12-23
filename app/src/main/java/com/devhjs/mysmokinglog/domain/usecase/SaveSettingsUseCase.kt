package com.devhjs.mysmokinglog.domain.usecase

import com.devhjs.mysmokinglog.core.util.Result
import com.devhjs.mysmokinglog.domain.model.UserSetting
import com.devhjs.mysmokinglog.domain.repository.UserSettingRepository
import javax.inject.Inject

class SaveSettingsUseCase @Inject constructor(
    private val repository: UserSettingRepository
) {
    suspend fun execute(settings: UserSetting): Result<Unit, Throwable> {
        return try {
            repository.saveSettings(settings)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
