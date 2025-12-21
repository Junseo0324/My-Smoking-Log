package com.devhjs.mysmokinglog.presentation.home

sealed interface HomeAction {
    object AddSmoking : HomeAction
    object DeleteSmoking : HomeAction
}