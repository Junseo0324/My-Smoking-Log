package com.devhjs.mysmokinglog.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_settings")
data class UserSettingEntity(
    @PrimaryKey
    val id: Int = 0,

    /** 하루 최대 개비 수 */
    val dailyLimit: Int,

    /** 한 갑 가격 (원) */
    val packPrice: Int,

    /** 한 갑당 개비 수 (기본 20) */
    val cigarettesPerPack: Int = 20
)
