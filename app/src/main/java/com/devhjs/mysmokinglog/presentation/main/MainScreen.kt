package com.devhjs.mysmokinglog.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devhjs.mysmokinglog.presentation.component.BottomNavigationBar
import com.devhjs.mysmokinglog.ui.AppColors

@Composable
fun MainScreen(
    selectedRoute: String?,
    onBottomNavSelected: (String) -> Unit,
    content: @Composable (Modifier) -> Unit
) {

    Scaffold(
        containerColor = AppColors.Background,
        bottomBar = {
            BottomNavigationBar(
                selectedRoute = selectedRoute,
                onItemClicked = onBottomNavSelected
            )
        }
    ) { innerPadding ->
        content(Modifier.padding(innerPadding))

    }

}