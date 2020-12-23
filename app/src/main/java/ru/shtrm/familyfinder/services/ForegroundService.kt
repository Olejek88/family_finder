package ru.shtrm.familyfinder.services

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log

class ForegroundService : Service() {
    private var sendGps: Handler? = null
    private var receiveLocations: Handler? = null
    private var receiveUsers: Handler? = null
    private var sendUser: Handler? = null

    override fun onCreate() {
        super.onCreate()

        Handler().postDelayed({ startSendLog() }, 0)
        Handler().postDelayed({ startReceiveLocation() }, 10000)
        Handler().postDelayed({ familyService() }, 20000)
        Handler().postDelayed({ userService() }, 30000)
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

    private fun startReceiveLocation() {
        val runnable = object : Runnable {
            override fun run() {
                val serviceIntent: Intent
                Log.d(TAG, "startReceiveLocations()")
                val context = applicationContext
                serviceIntent = Intent(context, ReceiveLocationsService::class.java)
                context.startService(serviceIntent)
                receiveLocations!!.postDelayed(this, RECEIVE_INTERVAL)
            }
        }
        receiveLocations = Handler()
        receiveLocations!!.postDelayed(runnable, RECEIVE_INTERVAL)
    }

    private fun familyService() {
        val runnable = object : Runnable {
            override fun run() {
                val serviceIntent: Intent
                Log.d(TAG, "startFamilyService()")
                val context = applicationContext
                serviceIntent = Intent(context, FamilyService::class.java)
                context.startService(serviceIntent)
                receiveUsers!!.postDelayed(this, RECEIVE_INTERVAL)
            }
        }
        receiveUsers = Handler()
        receiveUsers!!.postDelayed(runnable, RECEIVE_INTERVAL)
    }

    private fun userService() {
        val runnable = object : Runnable {
            override fun run() {
                val serviceIntent: Intent
                Log.d(TAG, "startUserService()")
                val context = applicationContext
                serviceIntent = Intent(context, UserService::class.java)
                context.startService(serviceIntent)
                sendUser!!.postDelayed(this, RECEIVE_INTERVAL)
            }
        }
        sendUser = Handler()
        sendUser!!.postDelayed(runnable, RECEIVE_INTERVAL)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        private val TAG = ForegroundService::class.java.simpleName
        private const val START_INTERVAL: Long = 60000
        private const val RECEIVE_INTERVAL: Long = 60000
    }
}
