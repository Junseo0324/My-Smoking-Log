package com.devhjs.mysmokinglog.domain.model

data class UserSetting(
    val dailyLimit: Int,
    val packPrice: Int,
    val cigarettesPerPackage: Int,
) {
    companion object {
        fun default() = UserSetting(
            dailyLimit = 20,
            packPrice = 4500,
            cigarettesPerPackage = 20
        )
    }
}