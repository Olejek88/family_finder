package ru.shtrm.familyfinder.services

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.realm.Realm
import org.json.JSONArray
import org.json.JSONObject
import ru.shtrm.familyfinder.BuildConfig
import ru.shtrm.familyfinder.data.database.AuthorizedUser
import ru.shtrm.familyfinder.data.database.repository.user.User
import ru.shtrm.familyfinder.data.network.ApiEndPoint
import ru.shtrm.familyfinder.data.network.ApiHeader


class FamilyService : Service() {
    private var isRunning: Boolean = false

    private val task = Runnable {
        val token : String? = AuthorizedUser.instance.token
        Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SERVER_USERS)
                .addHeaders(ApiHeader.ProtectedApiHeader(BuildConfig.API_KEY,AuthorizedUser.instance._id,AuthorizedUser.instance.token))
                .addHeaders("Authorization", "bearer ".plus(token))
                .addQueryParameter("XDEBUG_SESSION_START", "xdebug")
                .addBodyParameter("userLogin", AuthorizedUser.instance.login.toString())
                .build()
                .getAsJSONArray(object : JSONArrayRequestListener {
                    override fun onResponse(response: JSONArray?) {
                        val jsonArray = response
                        val realmD = Realm.getDefaultInstance()
                        if (!realmD.isClosed && jsonArray!=null && jsonArray.length()>0) {
                            for (i in 0 until jsonArray.length()) {
                                val user = jsonArray.getJSONObject(i)
                                val userPresent = realmD.where(User::class.java).equalTo("login", user.getString("email")).findFirst()
                                if (userPresent==null) {
                                    realmD.executeTransactionAsync(Realm.Transaction { realmB ->
                                        val userNew = realmB.createObject<User>(User::class.java, User.getLastId()+1)
                                        userNew.login = user.getString("email")
                                        userNew.username = user.getString("username")
                                        if(user.has("last_latitude") && !user.isNull("last_latitude"))
                                            userNew.lastLatitude = user.getString("last_latitude").toDouble()
                                        if(user.has("last_longitude") && !user.isNull("last_longitude"))
                                            userNew.lastLongitude = user.getString("last_longitude").toDouble()
                                        if(user.has("location") && !user.isNull("location"))
                                            userNew.location = user.getString("location")
                                        if(user.has("image") && !user.isNull("image"))
                                            userNew.image = user.getString("image")
                                    }, Realm.Transaction.OnError {
                                        Log.d("db",it.message)
                                    })
                                } else {
                                    realmD.beginTransaction()
                                    userPresent.username = user.getString("username")
                                    if(user.has("last_latitude") && !user.isNull("last_latitude"))
                                        userPresent.lastLatitude = user.getString("last_latitude").toDouble()
                                    if(user.has("last_longitude") && !user.isNull("last_longitude"))
                                        userPresent.lastLongitude = user.getString("last_longitude").toDouble()
                                    if(user.has("location") && !user.isNull("location"))
                                        userPresent.location = user.getString("location")
                                    if(user.has("image") && !user.isNull("image"))
                                        userPresent.image = user.getString("image")
                                    realmD.commitTransaction()
                                }
                            }
                            realmD.close()
                        }
                    }
                    override fun onError(error: ANError) {
                        if (error.errorCode == 401) {
                            sendTokenRequest()
                        }
                    }
                })
        stopSelf()
    }

    fun sendTokenRequest() {
        Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SERVER_TOKEN)
                .addHeaders(ApiHeader.ProtectedApiHeader(BuildConfig.API_KEY,AuthorizedUser.instance._id,AuthorizedUser.instance.token))
                .addBodyParameter("userLogin", AuthorizedUser.instance.login.toString())
                .addQueryParameter("XDEBUG_SESSION_START", "xdebug")
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        val authUser = AuthorizedUser.instance;
                        authUser.token = response?.get("token").toString()
                    }
                    override fun onError(error: ANError) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            sendTokenRequest()
                        }, 30000)
                    }
                })
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
