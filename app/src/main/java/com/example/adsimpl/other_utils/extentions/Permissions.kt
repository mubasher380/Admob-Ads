package com.example.adsimpl.other_utils.extentions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.example.adsimpl.other_utils.*

object Permissions {

    fun Context.hasPermission(permId: Int) = ContextCompat.checkSelfPermission(
        this,
        getPermissionString(permId)
    ) == PackageManager.PERMISSION_GRANTED

    fun getPermissionString(id: Int) = when (id) {
        PERMISSION_READ_STORAGE -> Manifest.permission.READ_EXTERNAL_STORAGE
        PERMISSION_WRITE_STORAGE -> Manifest.permission.WRITE_EXTERNAL_STORAGE
        PERMISSION_CAMERA -> Manifest.permission.CAMERA
        PERMISSION_RECORD_AUDIO -> Manifest.permission.RECORD_AUDIO
        PERMISSION_READ_CONTACTS -> Manifest.permission.READ_CONTACTS
        PERMISSION_WRITE_CONTACTS -> Manifest.permission.WRITE_CONTACTS
        PERMISSION_READ_CALENDAR -> Manifest.permission.READ_CALENDAR
        PERMISSION_WRITE_CALENDAR -> Manifest.permission.WRITE_CALENDAR
        PERMISSION_CALL_PHONE -> Manifest.permission.CALL_PHONE
        PERMISSION_READ_CALL_LOG -> Manifest.permission.READ_CALL_LOG
        PERMISSION_WRITE_CALL_LOG -> Manifest.permission.WRITE_CALL_LOG
        PERMISSION_GET_ACCOUNTS -> Manifest.permission.GET_ACCOUNTS
        PERMISSION_READ_SMS -> Manifest.permission.READ_SMS
        PERMISSION_SEND_SMS -> Manifest.permission.SEND_SMS
        PERMISSION_READ_PHONE_STATE -> Manifest.permission.READ_PHONE_STATE
        else -> ""
    }

}