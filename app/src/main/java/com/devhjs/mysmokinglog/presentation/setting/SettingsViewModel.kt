package com.devhjs.mysmokinglog.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.mysmokinglog.BuildConfig
import com.devhjs.mysmokinglog.core.util.Result
import com.devhjs.mysmokinglog.domain.model.UserSetting
import com.devhjs.mysmokinglog.domain.usecase.GetSettingsUseCase
import com.devhjs.mysmokinglog.domain.usecase.SaveSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val saveSettingsUseCase: SaveSettingsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    init {
        fetchSettings()
    }

    private fun fetchSettings() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = getSettingsUseCase.execute()
            _state.update {
                when (result) {
                    is Result.Success -> {
                        it.copy(
                            isLoading = false,
                            dailyLimit = result.data.dailyLimit,
                            packPrice = result.data.packPrice,
                            cigarettesPerPackage = result.data.cigarettesPerPackage,
                            appVersion = BuildConfig.VERSION_NAME
                        )
                    }
                    is Result.Error -> {
                        it.copy(isLoading = false)
                    }
                }
            }
        }
    }

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.ChangeDailyLimit -> {
                _state.update { it.copy(dailyLimit = action.limit) }
            }
            is SettingsAction.ChangePackPrice -> {
                _state.update { it.copy(packPrice = action.price) }
                saveSettings()
            }
            SettingsAction.SaveSettings -> {
                saveSettings()
            }
        }
    }

    private fun saveSettings() {
        viewModelScope.launch {
            val currentState = state.value
            saveSettingsUseCase.execute(
                UserSetting(
                    dailyLimit = currentState.dailyLimit,
                    packPrice = currentState.packPrice,
                    cigarettesPerPackage = currentState.cigarettesPerPackage
                )
            )
        }
    }
}
