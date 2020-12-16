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
import ru.shtrm.familyfinder.data.database.repository.user.User
import ru.shtrm.familyfinder.data.network.ApiEndPoint


class ReceiveLocationsService : Service() {
    private var isRuning: Boolean = false

    private val task = Runnable {
        val realm = Realm.getDefaultInstance()

        val users = realm.where(User::class.java).findAll()
        for (user in users) {

            Rx2AndroidNetworking.get(ApiEndPoint.NOMINATIM)
                    .addQueryParameter("lat", user.lastLatitude.toString())
                    .addQueryParameter("lat", user.lastLongitude.toString())
                    .build()
                    .getAsJSONArray(object : JSONArrayRequestListener {
                        override fun onResponse(response: JSONArray) {
                            val jsonObject = response.getJSONObject(0)
                            val value = jsonObject.optString("display_name")
                            val authUser = AuthorizedUser.instance
                            if (authUser._id!=user._id) {
                                authUser.location = value
                            }
                            realm.executeTransaction { realmB ->
                                user.location = value
                            }
                        }

                        override fun onError(error: ANError) {
                            // handle error
                        }
                    })
        }
        realm.close()
        stopSelf()
    }

    override fun onCreate() {
        isRuning = false
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent == null) {
            return START_STICKY
        }
        if (!isRuning) {
            Log.d(TAG, "Run logs sender service...")
            isRuning = true
            Thread(task).start()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        isRuning = false
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        private const val TAG = "SendGPSnLog"
    }
}
