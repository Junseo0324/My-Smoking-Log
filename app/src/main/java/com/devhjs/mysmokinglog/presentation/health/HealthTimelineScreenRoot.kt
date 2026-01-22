package com.devhjs.mysmokinglog.presentation.health

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HealthTimelineScreenRoot(
    viewModel: HealthTimelineViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    HealthTimelineScreen(
        state = state
    )

}