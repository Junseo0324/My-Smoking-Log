package com.devhjs.mysmokinglog.presentation.health

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HealthTimelineScreenRoot(
    viewModel: HealthTimelineViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    HealthTimelineScreen(
        state = state
    )

}