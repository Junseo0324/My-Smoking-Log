package com.devhjs.mysmokinglog.presentation.stat

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StatScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: StatViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is StatEvent.ShowAd -> {
                    val activity = context as? Activity
                    activity?.let {
                        event.adManager.showAdIfReady(it)
                    }
                }
            }
        }
    }
    
    StatScreen(
        state = state,
        modifier = modifier
    )
}