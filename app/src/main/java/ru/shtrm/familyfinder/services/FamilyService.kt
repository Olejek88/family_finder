package ru.shtrm.familyfinder.services

import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.BitmapRequestListener
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
import ru.shtrm.familyfinder.util.FileUtils
import java.io.File

class FamilyService : Service() {
    private var isRunning: Boolean = false

    private val task = Runnable {
        val token: String? = AuthorizedUser.instance.token
        Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SERVER_USERS)
                .addHeaders(ApiHeader.ProtectedApiHeader(BuildConfig.API_KEY, AuthorizedUser.instance._id, AuthorizedUser.instance.token))
                .addHeaders("Authorization", "bearer ".plus(token))
                .addQueryParameter("XDEBUG_SESSION_START", "xdebug")
                .addBodyParameter("userLogin", AuthorizedUser.instance.login.toString())
                .build()
                .getAsJSONArray(object : JSONArrayRequestListener {
                    override fun onResponse(response: JSONArray?) {
                        val realmD = Realm.getDefaultInstance()
                        if (!realmD.isClosed && response != null && response.length() > 0) {
                            for (i in 0 until response.length()) {
                                val user = response.getJSONObject(i)
                                val serverDate = user.getString("updated_at")
                                val userPresent = realmD.where(User::class.java).equalTo("login", user.getString("email")).findFirst()
                                if (userPresent == null) {
                                    realmD.executeTransactionAsync(Realm.Transaction { realmB ->
                                        val userNew = realmB.createObject<User>(User::class.java, User.getLastId() + 1)
                                        userNew.login = user.getString("email")
                                        userNew.username = user.getString("username")
                                        if (user.has("last_latitude") && !user.isNull("last_latitude"))
                                            userNew.lastLatitude = user.getString("last_latitude").toDouble()
                                        if (user.has("last_longitude") && !user.isNull("last_longitude"))
                                            userNew.lastLongitude = user.getString("last_longitude").toDouble()
                                        if (user.has("location") && !user.isNull("location"))
                                            userNew.location = user.getString("location")
                                        if (user.has("image") && !user.isNull("image"))
                                            userNew.image = user.getString("image")
                                    }, Realm.Transaction.OnSuccess {
                                        if (user.has("image") && !user.isNull("image"))
                                            loadImage(user.getString("image"), user.getString("image"))
                                    })
                                } else {
                                    val localDate = userPresent.changedAt

                                    realmD.beginTransaction()
                                    userPresent.username = user.getString("username")
                                    if (user.has("last_latitude") && !user.isNull("last_latitude"))
                                        userPresent.lastLatitude = user.getString("last_latitude").toDouble()
                                    if (user.has("last_longitude") && !user.isNull("last_longitude"))
                                        userPresent.lastLongitude = user.getString("last_longitude").toDouble()
                                    if (user.has("location") && !user.isNull("location"))
                                        userPresent.location = user.getString("location")
                                    if (user.has("image") && !user.isNull("image"))
                                        userPresent.image = user.getString("image")
                                    realmD.commitTransaction()

                                    val localImageFile = File(userPresent.imageFilePath, userPresent.image)
                                    if (localDate.time < serverDate.toLong() || !localImageFile.exists()) {
                                        loadImage(userPresent.image, userPresent.image)
                                    }
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
                .addHeaders(ApiHeader.ProtectedApiHeader(BuildConfig.API_KEY, AuthorizedUser.instance._id, AuthorizedUser.instance.token))
                .addBodyParameter("userLogin", AuthorizedUser.instance.login.toString())
                .addQueryParameter("XDEBUG_SESSION_START", "xdebug")
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        val authUser = AuthorizedUser.instance
                        authUser.token = response?.get("token").toString()
                    }

                    override fun onError(error: ANError) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            sendTokenRequest()
                        }, 30000)
                    }
                })
    }

    fun loadImage(url: String, imageFilePath: String) {
        Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_SERVER_GET_IMAGE.plus(url))
                .build()
                .getAsBitmap(object : BitmapRequestListener {
                    override fun onResponse(bitmap: Bitmap) {
                        FileUtils.storeNewImage(bitmap, applicationContext, 800, imageFilePath)
                    }
                    override fun onError(error: ANError) {
                        Log.d("rest","load image error")
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
