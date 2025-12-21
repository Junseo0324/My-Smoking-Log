package com.devhjs.mysmokinglog.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.mysmokinglog.domain.usecase.AddSmokingUseCase
import com.devhjs.mysmokinglog.domain.usecase.GetTodaySmokingInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTodaySmokingInfoUseCase: GetTodaySmokingInfoUseCase,
    private val addSmokingUseCase: AddSmokingUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            val smokingSummary = getTodaySmokingInfoUseCase.execute()

            _state.update {
                it.copy(
                    isLoading = false,
                    todayCount = smokingSummary.count,
                    dailyLimit = smokingSummary.dailyLimit,
                    lastSmokingTime = smokingSummary.lastSmokingTime
                )
            }

        }
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.AddSmoking -> {
                addSmoking()
            }
        }
    }

    private fun addSmoking() {
        viewModelScope.launch {
            addSmokingUseCase.execute()
            fetchData()
        }
    }


}