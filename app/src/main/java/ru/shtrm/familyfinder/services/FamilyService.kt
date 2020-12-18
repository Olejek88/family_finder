package ru.shtrm.familyfinder.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.realm.Realm
import org.json.JSONArray
import ru.shtrm.familyfinder.data.database.AuthorizedUser
import ru.shtrm.familyfinder.data.network.ApiEndPoint


class FamilyService : Service() {
    private var isRunning: Boolean = false

    private val task = Runnable {
        Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SERVER_USERS)
                .addQueryParameter("userId", AuthorizedUser.instance._id.toString())
                .build()
                .getAsJSONArray(object : JSONArrayRequestListener {
                    override fun onResponse(response: JSONArray?) {
                        val jsonArray = response
                        val realmD = Realm.getDefaultInstance()
                        if (!realmD.isClosed) {
                            realmD.executeTransaction { realmB ->
                            }
                            realmD.close()
                        }
                    }

                    override fun onError(error: ANError) {
                        Log.d("Tag", "")
                    }
                })
        stopSelf()
    }

    override fun onCreate() {
        isRunning = false
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent == null) {
            return START_STICKY
        }
        if (!isRunning) {
            Log.d(TAG, "Run logs sender service...")
            isRunning = true
            Thread(task).start()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        private const val TAG = "FamilyService"
    }
}
