package com.devhjs.mysmokinglog.presentation.stat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.mysmokinglog.domain.usecase.GetStatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class StatViewModel @Inject constructor(
    private val getStatUseCase: GetStatUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(StatState())
    val state: StateFlow<StatState> = _state.asStateFlow()

    init {
        loadStats()
    }


    private fun loadStats() {
        viewModelScope.launch {
            getStatUseCase.execute()
                .collect { stats ->
                    _state.value = stats
                }
        }
    }
    
    fun refresh() {
        loadStats()
    }
}
