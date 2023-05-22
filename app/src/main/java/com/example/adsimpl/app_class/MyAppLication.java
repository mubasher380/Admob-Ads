package com.example.adsimpl.app_class;

import androidx.annotation.NonNull;

import com.example.adsimpl.R;
import com.example.adsimpl.base.MyBaseApplication;

public class MyAppLication extends MyBaseApplication {
    @NonNull
    @Override
    public String getOpenId() {
        return getString(R.string.app_open_id);
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
