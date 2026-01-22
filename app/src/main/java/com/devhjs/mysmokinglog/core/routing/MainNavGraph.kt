package com.devhjs.mysmokinglog.core.routing

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.devhjs.mysmokinglog.presentation.health.HealthTimelineScreenRoot
import com.devhjs.mysmokinglog.presentation.home.HomeScreenRoot
import com.devhjs.mysmokinglog.presentation.license.LicenseScreen
import com.devhjs.mysmokinglog.presentation.setting.SettingScreenRoot
import com.devhjs.mysmokinglog.presentation.stat.StatScreenRoot

@Composable
fun MainNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = MainRoute.Home.route,
        modifier = modifier
    ) {
        composable(MainRoute.Home.route) {
            HomeScreenRoot()
        }
        composable(MainRoute.Stat.route) {
            StatScreenRoot(
                onNavigateToHealthTimeline = {
                    navController.navigate(MainRoute.HealthTimeline.route)
                }
            )
        }
        composable(MainRoute.Setting.route) {
            SettingScreenRoot(
                onNavigateToLicense = {
                    navController.navigate(MainRoute.License.route)
                }
            )
        }
        composable(MainRoute.License.route) {
            LicenseScreen()
        }
        composable(MainRoute.HealthTimeline.route) {
            HealthTimelineScreenRoot()
        }

    }

}