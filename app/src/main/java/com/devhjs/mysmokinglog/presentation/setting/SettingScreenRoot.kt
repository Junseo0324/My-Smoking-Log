package com.devhjs.mysmokinglog.presentation.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
    onNavigateToLicense: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    
    SettingScreen(
        state = state,
        onAction = viewModel::onAction,
        onNavigateToLicense = onNavigateToLicense,
        modifier = modifier
    )
}