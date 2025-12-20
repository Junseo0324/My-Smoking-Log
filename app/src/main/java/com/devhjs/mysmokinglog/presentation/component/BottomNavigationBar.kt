package com.devhjs.mysmokinglog.presentation.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import com.devhjs.mysmokinglog.core.routing.BottomNavItem
import com.devhjs.mysmokinglog.core.routing.MainRoute

@Composable
fun BottomNavigationBar(
    selectedRoute: String?,
    onItemClicked: (String)-> Unit,
) {
    val bottomNavItems = listOf(
        BottomNavItem(
            route = MainRoute.Home,
            label = "홈",
            icon = Icons.Default.Home
        ),
        BottomNavItem(
            route = MainRoute.Stat,
            label = "통계",
            icon = Icons.Default.Star
        ),
        BottomNavItem(
            route = MainRoute.Setting,
            label = "설정",
            icon = Icons.Default.Settings
        )
    )

    NavigationBar {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = selectedRoute == item.route.route,
                onClick = { onItemClicked(item.route.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                }
            )
        }
    }
}