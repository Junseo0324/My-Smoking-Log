package com.devhjs.mysmokinglog.presentation.stat

object VisitCounter {
    private var count = 0
    private const val THRESHOLD = 3

    fun increment() {
        count++
    }

    fun shouldShowAd(): Boolean {
        return count >= THRESHOLD
    }

    fun reset() {
        count = 0
    }
}
