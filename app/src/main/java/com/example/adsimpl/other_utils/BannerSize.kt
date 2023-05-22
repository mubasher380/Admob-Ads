package com.example.adsimpl.other_utils

import com.example.adsimpl.R
import com.google.android.gms.ads.AdSize

enum class BannerSize(val size: AdSize) {
    SMALL(AdSize.BANNER), MEDIUM(AdSize.FULL_BANNER), LARGE(AdSize.LARGE_BANNER)
}

sealed class NativeSize(val layoutId: Int, val adId: String) {
    class Small(adId: String) : NativeSize(R.layout.admob_small_native, adId)
    class Medium(adId: String) : NativeSize(R.layout.admob_medium_native, adId)
    class Large(adId: String) : NativeSize(R.layout.admob_orignal_native, adId)
    class Custom(layout: Int, adId: String) : NativeSize(layout, adId)
}