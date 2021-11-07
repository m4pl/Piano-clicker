package com.mapl.recorder.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import timber.log.Timber

class RecordService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Timber.d("--> RecordService onCreate")
    }
}
