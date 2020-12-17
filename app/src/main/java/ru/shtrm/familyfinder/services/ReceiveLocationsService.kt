package ru.shtrm.familyfinder.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.realm.Realm
import org.json.JSONObject
import ru.shtrm.familyfinder.data.database.AuthorizedUser
import ru.shtrm.familyfinder.data.database.repository.user.User
import ru.shtrm.familyfinder.data.network.ApiEndPoint


class ReceiveLocationsService : Service() {
    private var isRuning: Boolean = false

    private val task = Runnable {
        val realm = Realm.getDefaultInstance()

        val users = realm.where(User::class.java).findAll()
        for (user in users) {
            val userId = user._id
            Rx2AndroidNetworking.get(ApiEndPoint.NOMINATIM)
                    .addQueryParameter("lat", user.lastLatitude.toString())
                    .addQueryParameter("lon", user.lastLongitude.toString())
                    .addQueryParameter("zoom", "18")
                    .addQueryParameter("format", "jsonv2")
                    .build()
                    .getAsJSONObject(object : JSONObjectRequestListener {
                        override fun onResponse(response: JSONObject?) {
                            val jsonObject = response
                            var value = jsonObject!!.getString("display_name")
                            var count = 0
                            val tokens = value.split("/")
                            value = ""
                            for (t in tokens) {
                                value = value.plus(t).plus(',')
                                if(count>5) {

                                    break
                                }
                                count++
                            }

                            val authUser = AuthorizedUser.instance
                            if (authUser._id==userId) {
                                authUser.location = value
                            }
                            val realmD = Realm.getDefaultInstance()
                            if (!realmD.isClosed) {
                                realmD.executeTransaction { realmB ->
                                    val userD = realmB.where(User::class.java).equalTo("_id", userId).findFirst()
                                    userD!!.location = value
                                    //realmB.close()
                                }
                                realmD.close()
                            }
                        }

                        override fun onError(error: ANError) {
                            Log.d("Tag","")
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
