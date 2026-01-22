package com.devhjs.mysmokinglog.presentation.health

import android.os.Build
import android.os.CountDownTimer
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.mysmokinglog.domain.repository.SmokingRepository
import com.devhjs.mysmokinglog.domain.usecase.health.GetHealthRecoveryUseCase
import com.devhjs.mysmokinglog.presentation.util.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class HealthTimelineViewModel @Inject constructor(
    private val getHealthRecoveryUseCase: GetHealthRecoveryUseCase,
    private val smokingRepository: SmokingRepository 
) : ViewModel() {

    private val _state = MutableStateFlow(HealthTimelineState())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val milestones = getHealthRecoveryUseCase()
            val lastEvent = smokingRepository.getLastSmokingEventItem()
            val lastSmokingTime = lastEvent?.timestamp ?: System.currentTimeMillis()
            
            _state.update { 
                it.copy(
                    milestones = milestones,
                    timeSinceLastSmoking = TimeFormatter.formatTimeAgo(lastSmokingTime)
                ) 
            }
        }
    }
}
