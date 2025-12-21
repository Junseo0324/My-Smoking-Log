package com.devhjs.mysmokinglog.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.mysmokinglog.core.util.Result

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
            
            when(val result = getTodaySmokingInfoUseCase.execute()) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            todayCount = result.data.count,
                            dailyLimit = result.data.dailyLimit,
                            lastSmokingTime = result.data.lastSmokingTime
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

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.AddSmoking -> {
                addSmoking()
            }
        }
    }

    private fun addSmoking() {
        viewModelScope.launch {
            when(val result = addSmokingUseCase.execute()) {
                is Result.Success -> {
                    fetchData()
                }
                is Result.Error -> {
                    // 에러 처리 로직 추가
                }
            }
        }
    }


}