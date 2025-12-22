package com.devhjs.mysmokinglog.presentation.stat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun StatScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: StatViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    StatScreen(
        state = state,
        modifier = modifier
    )
}