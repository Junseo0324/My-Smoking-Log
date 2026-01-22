package com.devhjs.mysmokinglog.presentation.license

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devhjs.mysmokinglog.presentation.designsystem.AppColors
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer

@Composable
fun LicenseScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        containerColor = AppColors.Background,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LibrariesContainer(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}
