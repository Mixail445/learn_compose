package com.example.learn_compose.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class KtorService : Service() {
    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        val extras = intent?.extras?.getBoolean("startKtor")
        startKtorServer(this, extras ?: false)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        startKtorServer(this, false)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
