package com.devhjs.mysmokinglog.data.mapper

import com.devhjs.mysmokinglog.data.entity.SmokingEntity
import com.devhjs.mysmokinglog.domain.model.Smoking

fun Smoking.toEntity(): SmokingEntity {
    return SmokingEntity(
        timestamp = timestamp,
        date = date
    )
}

fun SmokingEntity.toModel(): Smoking {
    return Smoking(
        timestamp = timestamp,
        date = date
    )
}