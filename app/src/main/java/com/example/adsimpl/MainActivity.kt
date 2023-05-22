package com.example.adsimpl

import android.os.Bundle
import com.example.adsimpl.ads.LoadNativeAd
import com.example.adsimpl.base.BaseActivity
import com.example.adsimpl.databinding.ActivityMainBinding
import com.example.adsimpl.other_utils.BannerSize
import com.example.adsimpl.other_utils.NativeSize

class MainActivity : BaseActivity() {
    lateinit var binding: ActivityMainBinding
    override fun getInterstitialId1(): String {
        return getString(R.string.app_interstitial_id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        elapseTime = 20
        LoadNativeAd(
            this,
            binding.adContainerSmall,
            NativeSize.Small(getString(R.string.admob_native))
        )

        loadBanner(getString(R.string.admob_bqnner), binding.adContainerLarge, BannerSize.SMALL)
        LoadNativeAd(
            this,
            binding.adContainerMedium,
            NativeSize.Medium(getString(R.string.admob_native))
        )
        LoadNativeAd(
            this,
            binding.adContainerLarge,
            NativeSize.Large(getString(R.string.admob_native))
        )
        LoadNativeAd(
            this,
            binding.adContainerLarge,
            NativeSize.Custom(R.layout.admob_banner_native, getString(R.string.admob_native))
        )

        binding.button.setOnClickListener {
            showFullAd {
                showToastShort("after ad")
            }
        }

    }
}