package com.devhjs.mysmokinglog.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devhjs.mysmokinglog.presentation.component.BottomNavigationBar

@Composable
fun MainScreen(
    selectedRoute: String?,
    onBottomNavSelected: (String) -> Unit,
    content: @Composable (Modifier) -> Unit
) {

    Scaffold(
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