package com.devhjs.mysmokinglog.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.devhjs.mysmokinglog.core.routing.MainNavGraph

@Composable
fun MainScreenRoot() {
    val navController = rememberNavController()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    MainScreen(
        selectedRoute = currentRoute,
        onBottomNavSelected = { route ->
            navController.navigate(route) {
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    ) { modifier ->
        MainNavGraph(
            navController = navController,
            modifier = modifier
        )
    }

}