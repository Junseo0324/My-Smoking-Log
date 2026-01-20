package com.devhjs.mysmokinglog.data.mapper

import com.devhjs.mysmokinglog.data.local.entity.UserSettingEntity
import com.devhjs.mysmokinglog.domain.model.UserSetting

fun UserSetting.toEntity(): UserSettingEntity {
    return UserSettingEntity(
        dailyLimit = dailyLimit,
        packPrice = packPrice,
        cigarettesPerPack = cigarettesPerPackage
    )
}

fun UserSettingEntity.toModel(): UserSetting {
    return UserSetting(
        dailyLimit = dailyLimit,
        packPrice = packPrice,
        cigarettesPerPackage = cigarettesPerPack
    )
}