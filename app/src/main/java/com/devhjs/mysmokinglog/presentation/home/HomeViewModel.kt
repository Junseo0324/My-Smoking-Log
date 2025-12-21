package com.devhjs.mysmokinglog.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.mysmokinglog.core.util.Result

import com.devhjs.mysmokinglog.domain.usecase.AddSmokingUseCase
import com.devhjs.mysmokinglog.domain.usecase.DeleteSmokingUseCase
import com.devhjs.mysmokinglog.domain.usecase.GetTodaySmokingInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTodaySmokingInfoUseCase: GetTodaySmokingInfoUseCase,
    private val addSmokingUseCase: AddSmokingUseCase,
    private val deleteSmokingUseCase: DeleteSmokingUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            
            getTodaySmokingInfoUseCase().collect { result ->
                when(result) {
                    is Result.Success -> {
                        _state.update {
                            val status = calculateSmokingStatus(result.data.count, result.data.dailyLimit)
                            it.copy(
                                isLoading = false,
                                todayCount = result.data.count,
                                dailyLimit = result.data.dailyLimit,
                                lastSmokingTime = result.data.lastSmokingTime,
                                status = status
                            )
                        }
                    }
                    is Result.Error -> {
                        _state.update {
                            it.copy(isLoading = false)
                        }
                        // 에러 처리 로직 추가
                    }
                }
            }
        }
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.AddSmoking -> {
                addSmoking()
            }

            HomeAction.DeleteSmoking -> {
                deleteSmokingHistory()
            }
        }
    }

    private var undoJob: Job? = null

    private fun addSmoking() {
        viewModelScope.launch {
            val result = addSmokingUseCase.execute()
            if (result is Result.Success) {
                undoJob?.cancel()
                _state.update { it.copy(isUndoVisible = true) }
                undoJob = launch {
                    delay(3000L)
                    _state.update { it.copy(isUndoVisible = false) }
                }
            } else if (result is Result.Error) {
                // 에러 처리
            }
        }
    }

    private fun deleteSmokingHistory() {
        _state.update { it.copy(isUndoVisible = false) }
        undoJob?.cancel()
        
        viewModelScope.launch {
            val result = deleteSmokingUseCase.execute()
            if (result is Result.Error) {
                // 에러 처리
            }
        }
    }

    private fun calculateSmokingStatus(count: Int, limit: Int): SmokingStatus {
        if (limit == 0) return SmokingStatus.EXCEEDED
        
        return when {
            count > limit -> SmokingStatus.EXCEEDED
            count >= limit * 0.8 -> SmokingStatus.WARNING
            else -> SmokingStatus.SAFE
        }
    }
}