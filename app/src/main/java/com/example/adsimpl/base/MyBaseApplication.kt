package com.example.adsimpl.base

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.adsimpl.ads.AppOpenAdManager

import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp

abstract class MyBaseApplication() : Application(),
    Application.ActivityLifecycleCallbacks,

    LifecycleObserver {

    var isInterstitialAdShowing = false

    val appOpenId: String
        get() = getOpenId()

    abstract fun getOpenId(): String

    var context: Context? = null
    private lateinit var appOpenAdManager: AppOpenAdManager
    private var currentActivity: Activity? = null

    override fun onCreate() {
        super.onCreate()
        context = this

        MobileAds.initialize(this) { }
        appOpenAdManager = AppOpenAdManager(appOpenId)
        registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(defaultLifecycleObserver)
        try {
            FirebaseApp.initializeApp(this)
        } catch (e: Exception) {
            Log.i("firebase_error_tag", "${e.message}")
        }
    }


    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(activity: Activity) {
        // Updating the currentActivity only when an ad is not showing.
        if (!appOpenAdManager.isShowingAd) {
            currentActivity = activity
        }
    }

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}
    var defaultLifecycleObserver = object : DefaultLifecycleObserver {

        override fun onStart(owner: LifecycleOwner) {
            super.onStart(owner)
            //your code here
            currentActivity?.let {
                //  Toast.makeText(this, "show ad", Toast.LENGTH_SHORT).show()
                if (!isInterstitialAdShowing)
                    showAdIfAvailable(it)
                Log.d("AppOpenAd", "onMoveToForeground: ")
            }
        }

        override fun onStop(owner: LifecycleOwner) {
            super.onStop(owner)
            //your code here
        }
    }

    /** Show the ad if one isn't already showing. */
    fun showAdIfAvailable(activity: Activity) {
        appOpenAdManager.showAdIfAvailable(
            activity,
            object : AppOpenAdManager.OnShowAdCompleteListener {
                override fun onShowAdComplete() {
                    // Empty because the user will go back to the activity that shows the ad.
                }
            })
    }

}