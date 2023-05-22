package com.example.adsimpl.ads

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.adsimpl.R
import com.example.adsimpl.other_utils.NativeSize
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView

class LoadNativeAd(
    val activity: Activity,
    val frameLayout: FrameLayout,
    val adSize: NativeSize
) {
    private var nativeAd: NativeAd? = null

    init {
        refreshAd()
        Toast.makeText(activity, "ad called", Toast.LENGTH_SHORT).show()
    }

    private fun refreshAd() {
        val builder = AdLoader.Builder(activity, adSize.adId)
        builder.forNativeAd { unifiedNativeAd ->
            nativeAd?.destroy()
            Toast.makeText(activity, "ad loaded", Toast.LENGTH_SHORT).show()
            nativeAd = unifiedNativeAd

            @SuppressLint("InflateParams") val adView = activity.layoutInflater.let {
                //    if (adSize == NativeSize.Custom)
                val layout = when (adSize) {
                    is NativeSize.Custom ->
                        it.inflate(adSize.layoutId, null) as ConstraintLayout
                    else -> {
                        it.inflate(adSize.layoutId, null) as NativeAdView
                    }
                }

                val view = NativeAdView(activity)
                view.addView(layout)
                view
            }
            populateUnifiedNativeAdView(unifiedNativeAd, adView)
            frameLayout.removeAllViews()
            frameLayout.addView(adView)
        }.build()
        val adOptions = NativeAdOptions.Builder().build()
        builder.withNativeAdOptions(adOptions)
        val adLoader = builder.withAdListener(object : AdListener() {

        }).build()
        adLoader.loadAd(AdRequest.Builder().build())
    }

    private fun populateUnifiedNativeAdView(nativeAd: NativeAd, adView: NativeAdView) {


        if (adView.findViewById<MediaView>(R.id.ad_media) != null) {
            val mediaView: MediaView = adView.findViewById(R.id.ad_media)
            adView.mediaView = mediaView
        }

        // Set other ad assets.
        adView.headlineView = adView.findViewById(R.id.native_ad_title)
        adView.bodyView = adView.findViewById(R.id.native_ad_social_context)
        adView.callToActionView = adView.findViewById(R.id.native_ad_call_to_action)
        adView.iconView = adView.findViewById(R.id.native_icon_view)

        //Headline
        if (adView.headlineView != null) {
            (adView.headlineView as TextView).text = nativeAd.headline
            (adView.headlineView as TextView).isSelected = true
        }


        //Body
        if (adView.bodyView != null) {
            if (nativeAd.body == null) {
                adView.bodyView?.visibility = View.INVISIBLE
            } else {
                adView.bodyView?.visibility = View.VISIBLE
                (adView.bodyView as TextView).text = nativeAd.body
            }
        }

        //Call to Action
        if (adView.callToActionView != null) {
            if (nativeAd.callToAction == null) {
                adView.callToActionView?.visibility = View.INVISIBLE
            } else {
                adView.callToActionView?.visibility = View.VISIBLE
                (adView.callToActionView as TextView).text = nativeAd.callToAction
            }
        }

        //Icon
        if (adView.iconView != null) {
            if (nativeAd.icon == null) {
                adView.iconView?.visibility = View.INVISIBLE
            } else {
                (adView.iconView as ImageView).setImageDrawable(nativeAd.icon?.drawable)
                adView.iconView?.visibility = View.VISIBLE
            }
        }

        //price
        if (adView.priceView != null) {
            if (nativeAd.price == null) {
                adView.priceView?.visibility = View.GONE
            } else {
                adView.priceView?.visibility = View.GONE
                (adView.priceView as TextView).text = nativeAd.price
            }
        }

        //Store
        if (adView.storeView != null) {
            if (nativeAd.store == null) {
                adView.storeView?.visibility = View.GONE
            } else {
                adView.storeView?.visibility = View.GONE
                (adView.storeView as TextView).text = nativeAd.store
            }
        }

        //Rating
        if (adView.starRatingView != null) {
            if (nativeAd.starRating != null) {
                (adView.starRatingView as RatingBar).rating = nativeAd.starRating?.toFloat()!!
            }
            adView.starRatingView?.visibility = View.GONE
        }

        //Advertiser
        if (adView.advertiserView != null) {
            if (nativeAd.advertiser != null) {
                (adView.advertiserView as TextView).text = nativeAd.advertiser
            }
            adView.advertiserView?.visibility = View.GONE
        }
        adView.setNativeAd(nativeAd)
    }
}