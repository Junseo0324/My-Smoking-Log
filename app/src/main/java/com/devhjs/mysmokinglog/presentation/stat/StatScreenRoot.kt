package com.devhjs.mysmokinglog.presentation.stat

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devhjs.mysmokinglog.BuildConfig
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

@Composable
fun StatScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: StatViewModel = hiltViewModel(),
    onNavigateToHealthTimeline: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    
    // AdMob Logic
    val activity = context as? Activity
    var interstitialAd by remember { mutableStateOf<InterstitialAd?>(null) }
    
    // 광고 로드 함수
    fun loadAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            context,
            BuildConfig.ADMOB_INTERSTITIAL_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    interstitialAd = null
                }

                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                }
            }
        )
    }

    // 초기 진입 시 방문 횟수 증가 및 광고 로드
    LaunchedEffect(Unit) {
        VisitCounter.increment() // 증가

        if (interstitialAd == null) {
            loadAd()
        }
    }

    // 광고가 로드되었고, 방문 횟수가 충족되면 광고 표시
    LaunchedEffect(interstitialAd) {
        val shouldShow = VisitCounter.shouldShowAd()
        val hasAd = interstitialAd != null

        if (shouldShow && hasAd) {
            activity?.let {
                interstitialAd?.show(it)
                interstitialAd = null // 표시 후 제거
                VisitCounter.reset()
            }
        }
    }
    
    StatScreen(
        state = state,
        onNavigateToHealthTimeline = onNavigateToHealthTimeline,
        modifier = modifier
    )
}