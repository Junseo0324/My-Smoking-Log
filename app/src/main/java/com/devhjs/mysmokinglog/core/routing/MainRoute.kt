package com.devhjs.mysmokinglog.core.routing

sealed class MainRoute(val route: String) {
    data object Home: MainRoute("home")
    data object Stat: MainRoute("stat")
    data object Setting: MainRoute("setting")
    data object License: MainRoute("license")
}