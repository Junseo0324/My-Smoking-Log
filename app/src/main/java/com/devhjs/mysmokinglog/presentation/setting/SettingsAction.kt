package com.devhjs.mysmokinglog.presentation.setting

sealed interface SettingsAction {
    data class ChangeDailyLimit(val limit: Int) : SettingsAction
    data class ChangePackPrice(val price: Int) : SettingsAction
    data object SaveSettings : SettingsAction
}
