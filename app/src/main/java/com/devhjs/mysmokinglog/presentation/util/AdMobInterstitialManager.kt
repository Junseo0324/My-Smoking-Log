package com.devhjs.mysmokinglog.presentation.util

import android.app.Activity
import android.content.Context
import android.util.Log
import com.devhjs.mysmokinglog.BuildConfig
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdMobInterstitialManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var interstitialAd: InterstitialAd? = null
    private var visitCount = 0
    private val threshold = 2 // 2번 방문마다 광고 표시

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

    fun showAdIfReady(activity: Activity) {
        visitCount++
        if (visitCount >= threshold) {
            if (interstitialAd != null) {
                interstitialAd?.show(activity)
                visitCount = 0 // 카운트 초기화
                loadAd() // 다음을 위해 미리 로드
            } else {
                // 광고가 아직 로드되지 않았으면 그냥 넘어감 (또는 로드 시도)
                loadAd()
            }
        }
    }
}
