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

    // 초기 진입 시 광고 로드
    LaunchedEffect(Unit) {
        if (interstitialAd == null) {
            loadAd()
        }
    }

    // 방문 횟수 체크 및 표시 로직 (SharedPreferences 등을 쓰지 않고 간단히 메모리 상에서 처리하려면 static 변수가 필요하거나, 
    // 여기서는 화면이 재구성될 때마다 실행되므로 방문 시마다 체크하는 로직을 별도 객체 없이 구현하기 위해 
    // 방문 횟수를 저장할 곳이 필요함. 하지만 ScreenRoot는 매번 그려질 수 있음. 
    // 간단하게 "화면에 진입할 때마다"를 구현하기 위해 static 변수 대용으로 companion object나 외부 변수를 쓸 수 없으니
    // 매번 로드/표시 시도 로직을 수행.
    // 기존 로직(3번마다)을 유지하려면 별도 저장소가 필요하므로, 간단히 "VisitCounter" 객체를 파일 최상위에 private으로 선언하여 사용.

    LaunchedEffect(Unit) {
        VisitCounter.increment()
        if (VisitCounter.shouldShowAd()) {
            if (interstitialAd != null) {
                activity?.let { interstitialAd?.show(it) }
                interstitialAd = null // 표시 후 제거
                VisitCounter.reset()
                loadAd() // 다음을 위해 리로드
            } else {
                loadAd() // 없으면 로드 시도
            }
        }
    }
    
    StatScreen(
        state = state,
        onNavigateToHealthTimeline = onNavigateToHealthTimeline,
        modifier = modifier
    )
}