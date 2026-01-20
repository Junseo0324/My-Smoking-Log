package com.devhjs.mysmokinglog.presentation.stat

import com.devhjs.mysmokinglog.presentation.util.AdMobInterstitialManager

sealed interface StatEvent {
    data class ShowAd(val adManager: AdMobInterstitialManager) : StatEvent
}