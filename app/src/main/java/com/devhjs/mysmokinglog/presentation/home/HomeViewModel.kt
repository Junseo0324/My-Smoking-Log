package com.devhjs.mysmokinglog.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.mysmokinglog.core.util.Result
import com.devhjs.mysmokinglog.core.util.throttleFirst
import com.devhjs.mysmokinglog.domain.usecase.AddSmokingUseCase
import com.devhjs.mysmokinglog.domain.usecase.DeleteSmokingUseCase
import com.devhjs.mysmokinglog.domain.usecase.GetTodaySmokingInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTodaySmokingInfoUseCase: GetTodaySmokingInfoUseCase,
    private val addSmokingUseCase: AddSmokingUseCase,
    private val deleteSmokingUseCase: DeleteSmokingUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()


    private val _deleteEvent = MutableSharedFlow<Unit>()
    
    init {
        fetchData()
        
        _deleteEvent
            .throttleFirst(500L)
            .onEach {
                _state.update { it.copy(isUndoVisible = false) }
                deleteSmokingUseCase.execute()
            }
            .launchIn(viewModelScope)
    }



    fun fetchData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            getTodaySmokingInfoUseCase.execute().collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        val data = result.data
                        val status = calculateSmokingStatus(
                            count = data.count,
                            limit = data.dailyLimit
                        )

                        _state.update {
                            it.copy(
                                isLoading = false,
                                todayCount = data.count,
                                dailyLimit = data.dailyLimit,
                                lastSmokingTimestamp = data.lastSmokingTimestamp,
                                status = status,
                                observedDate = LocalDate.now().toString()
                            )
                        }
                    }

                    is Result.Error -> {
                        _state.update { it.copy(isLoading = false) }
                    }
                }
            }
        }
    }

    fun checkData() {
        fetchData()
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.AddSmoking -> addSmoking()
            HomeAction.DeleteSmoking -> deleteSmokingHistory()
        }
    }

    private fun addSmoking() {
        viewModelScope.launch {
            val result = addSmokingUseCase.execute()
            if (result is Result.Success) {
                showUndoTemporarily()
            } else {

            }
        }
    }

    private fun deleteSmokingHistory() {
        viewModelScope.launch {
            _deleteEvent.emit(Unit)
        }
    }

    private fun showUndoTemporarily() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isUndoVisible = true,
                )
            }
            delay(3_000L)
            _state.update {
                it.copy(
                    isUndoVisible = false,
                )
            }
        }
    }

    private fun calculateSmokingStatus(
        count: Int,
        limit: Int
    ): SmokingStatus {
        if (limit == 0) return SmokingStatus.EXCEEDED

        return when {
            count > limit -> SmokingStatus.EXCEEDED
            count >= limit * 0.8 -> SmokingStatus.WARNING
            else -> SmokingStatus.SAFE
        }
    }
}