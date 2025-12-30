package com.devhjs.mysmokinglog.presentation.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.devhjs.mysmokinglog.R
import com.devhjs.mysmokinglog.core.routing.BottomNavItem
import com.devhjs.mysmokinglog.core.routing.MainRoute
import com.devhjs.mysmokinglog.ui.AppColors

@Composable
fun BottomNavigationBar(
    selectedRoute: String?,
    onItemClicked: (String)-> Unit,
) {
    val bottomNavItems = listOf(
        BottomNavItem(
            route = MainRoute.Home,
            label = stringResource(R.string.nav_home),
            icon = painterResource(R.drawable.home)
        ),
        BottomNavItem(
            route = MainRoute.Stat,
            label = stringResource(R.string.nav_stat),
            icon = painterResource(R.drawable.stat)
        ),
        BottomNavItem(
            route = MainRoute.Setting,
            label = stringResource(R.string.nav_setting),
            icon = painterResource(R.drawable.setting)
        )
    )

    NavigationBar(
        containerColor = AppColors.Black08
    ) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = selectedRoute == item.route.route,
                onClick = { onItemClicked(item.route.route) },
                icon = {
                    Icon(
                        painter = item.icon,
                        contentDescription = item.label
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AppColors.White,
                    unselectedIconColor = AppColors.Gray,
                    indicatorColor = Color.Transparent,
                )
            )
        }
    }
}