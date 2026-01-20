package com.devhjs.mysmokinglog.presentation.stat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.mysmokinglog.core.util.UiText
import com.devhjs.mysmokinglog.domain.model.StatInfo
import com.devhjs.mysmokinglog.domain.usecase.GetStatUseCase
import com.devhjs.mysmokinglog.presentation.util.AdMobInterstitialManager
import com.devhjs.mysmokinglog.presentation.util.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class StatViewModel @Inject constructor(
    private val getStatUseCase: GetStatUseCase,
    private val adManager: AdMobInterstitialManager
) : ViewModel() {

    private val _state = MutableStateFlow(StatState())
    val state: StateFlow<StatState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<StatEvent>()
    val event = _event.asSharedFlow()

    init {
        loadStats()
        triggerAd()
    }

    private fun triggerAd() {
        viewModelScope.launch {
            _event.emit(StatEvent.ShowAd(adManager))
        }
    }

    private fun loadStats() {
        viewModelScope.launch {
            getStatUseCase.execute()
                .collect { info ->
                    _state.value = mapToState(info)
                }
        }
    }

    private fun mapToState(info: StatInfo): StatState {
        return StatState(
            streak = UiText.DynamicString(info.streak.toString()),
            averageSmokingInterval = TimeFormatter.formatAverageInterval(info.averageSmokingInterval),
            longestStreak = TimeFormatter.formatDuration(info.longestStreak),
            thisMonthCost = TimeFormatter.formatCurrency(info.thisMonthCost),
            cigarettesTotalCount = info.cigarettesTotalCount,
            packCount = info.packCount,
            weeklyCigarettes = info.weeklyCigarettes,
            todayTime = info.todayTime
        )
    }
}
