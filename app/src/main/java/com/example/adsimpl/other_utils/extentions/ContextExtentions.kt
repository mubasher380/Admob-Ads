package com.example.adsimpl.other_utils.extentions

import android.app.Activity
import android.util.Log
import androidx.annotation.NonNull
import com.example.adsimpl.base.BaseActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import java.util.*
import java.util.concurrent.TimeUnit

object ContextExtentions {


    fun Activity.loadInterstitial() {
        Log.i("mInterstitialAd", "called")
        val adRequest: AdRequest = AdRequest.Builder().build()
        InterstitialAd.load(this, (this as BaseActivity).interstitialId, adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(@NonNull interstitialAd: InterstitialAd) {
                    this@loadInterstitial.mInterstitialAd = interstitialAd
                    Log.i("mInterstitialAd", "onAdLoaded: add  loade")
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    mInterstitialAd = null
                    Log.i("mInterstitialAd", "onAdLoaded: ${error.message}")
                }
            })
    }




    fun timeDifference(millis: Long): Int {
        val current = Calendar.getInstance().timeInMillis
        val elapsedTime = current - millis

        return TimeUnit.MILLISECONDS.toSeconds(elapsedTime).toInt()
    }

}