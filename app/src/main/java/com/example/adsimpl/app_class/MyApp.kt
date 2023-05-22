package com.example.adsimpl.app_class

import com.example.adsimpl.R
import com.example.adsimpl.base.MyBaseApplication

class MyApp : MyBaseApplication() {

    override fun getOpenId(): String {
        return getString(R.string.app_open_id)
    }

    override fun onCreate() {
        super.onCreate()

    }
}