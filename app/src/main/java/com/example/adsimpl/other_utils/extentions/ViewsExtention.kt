package com.example.adsimpl.other_utils.extentions

import android.os.SystemClock
import android.view.View
import com.example.adsimpl.other_utils.lastClickTime


object ViewsExtention {

    fun View.setOnOneClickListener(debounceTime: Long = 500L, action: (v: View) -> Unit) {
        this.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View) {
                if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
                else action(v)
                lastClickTime = SystemClock.elapsedRealtime()
            }
        })
    }


    fun View.visible() {
        visibility = View.VISIBLE
    }

    fun View.gone() {
        visibility = View.GONE
    }

    fun View.inVisible() {
        visibility = View.INVISIBLE
    }




}