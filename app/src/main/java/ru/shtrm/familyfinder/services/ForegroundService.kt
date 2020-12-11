package ru.shtrm.familyfinder.services

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log

class ForegroundService : Service() {
    private var sendGps: Handler? = null

    override fun onCreate() {
        super.onCreate()

        Handler().postDelayed({ startSendLog() }, 0)
    }

    private fun startSendLog() {
        val runnable = object : Runnable {
            override fun run() {
                val serviceIntent: Intent
                Log.d(TAG, "startSendGPS()")
                val context = applicationContext
                serviceIntent = Intent(context, SendGPSService::class.java)
                context.startService(serviceIntent)

                sendGps!!.postDelayed(this, START_INTERVAL)
            }
        }
        sendGps = Handler()
        sendGps!!.postDelayed(runnable, START_INTERVAL)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        private val TAG = ForegroundService::class.java.simpleName
        private val START_INTERVAL: Long = 60000
    }
}
