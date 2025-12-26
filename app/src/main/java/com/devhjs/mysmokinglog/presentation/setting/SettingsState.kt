package com.devhjs.mysmokinglog.presentation.setting

data class SettingsState(
    val dailyLimit: Int = 20,
    val packPrice: Int = 4500,
    val cigarettesPerPackage: Int = 20,
    val isLoading: Boolean = false,
    val appVersion: String = ""
)
