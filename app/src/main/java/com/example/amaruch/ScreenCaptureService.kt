package com.example.amaruch

import android.app.Service
import android.content.Intent
import android.os.IBinder

class ScreenCaptureService: Service() {
    override fun onBind(intent: Intent?): IBinder? = null
}