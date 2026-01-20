package com.devhjs.mysmokinglog.presentation.widget

import android.content.Context
import com.devhjs.mysmokinglog.data.local.AppDatabase
import com.devhjs.mysmokinglog.data.repository.SmokingRepositoryImpl
import com.devhjs.mysmokinglog.data.repository.UserSettingRepositoryImpl
import com.devhjs.mysmokinglog.domain.repository.SmokingRepository
import com.devhjs.mysmokinglog.domain.usecase.AddSmokingUseCase
import com.devhjs.mysmokinglog.domain.usecase.GetTodaySmokingWidgetInfoUseCase
import java.time.Clock

object WidgetServiceLocator {

    fun smokingRepository(context: Context): SmokingRepository {
        val db = AppDatabase.getInstance(context)
        return SmokingRepositoryImpl(db.smokingDao())
    }

    fun addSmokingUseCase(context: Context): AddSmokingUseCase {
        return AddSmokingUseCase(
            smokingRepository = smokingRepository(context),
            clock = Clock.systemDefaultZone()
        )
    }

    fun getTodaySmokingWidgetUseCase(context: Context): GetTodaySmokingWidgetInfoUseCase {
        val db = AppDatabase.getInstance(context)
        return GetTodaySmokingWidgetInfoUseCase(
            smokingRepository = SmokingRepositoryImpl(db.smokingDao()),
            userSettingRepository = UserSettingRepositoryImpl(db.userSettingsDao()),
            clock = Clock.systemDefaultZone()
        )
    }

}