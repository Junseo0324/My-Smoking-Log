package com.devhjs.mysmokinglog.domain.model

data class UserSetting(
    val dailyLimit: Int,
    val packPrice: Int,
    val cigarettesPerPackage: Int,
)