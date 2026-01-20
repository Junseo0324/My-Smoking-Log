package com.devhjs.mysmokinglog.presentation.stat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StatScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: StatViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = androidx.compose.ui.platform.LocalContext.current
    
    androidx.compose.runtime.LaunchedEffect(Unit) {
        val activity = context as? android.app.Activity
        activity?.let {
            viewModel.showAd(it)
        }
    }
    
    StatScreen(
        state = state,
        modifier = modifier
    )
}