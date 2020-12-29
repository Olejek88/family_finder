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
import ru.shtrm.familyfinder.BuildConfig
import ru.shtrm.familyfinder.data.database.AuthorizedUser
import ru.shtrm.familyfinder.data.database.repository.user.User
import ru.shtrm.familyfinder.data.network.ApiEndPoint
import ru.shtrm.familyfinder.data.network.ApiHeader
import ru.shtrm.familyfinder.data.network.UserRequest
import ru.shtrm.familyfinder.util.FileUtils
import java.io.File

class UserService : Service() {
    private var isRunning: Boolean = false

    private val task = Runnable {
        if (!AuthorizedUser.instance.isSent) {
            val token: String? = AuthorizedUser.instance.token
            val realm = Realm.getDefaultInstance()
            val userC = realm.where(User::class.java).equalTo("login", AuthorizedUser.instance.login).findFirst()
            val user = realm.copyFromRealm(userC!!)
            if (user != null) {
                Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SERVER_USER_SEND)
                        .addHeaders(ApiHeader.ProtectedApiHeader(BuildConfig.API_KEY, AuthorizedUser.instance._id, AuthorizedUser.instance.token))
                        .addHeaders("Authorization", "bearer ".plus(token))
                        .addApplicationJsonBody(UserRequest.SendUserRequest(user = user))
                        .addQueryParameter("XDEBUG_SESSION_START", "xdebug")
                        .build()
                        .getAsJSONObject(object : JSONObjectRequestListener {
                            override fun onResponse(response: JSONObject?) {
                                AuthorizedUser.instance.isSent = true
                            }
                            override fun onError(error: ANError) {
                            }
                        })
            }
            realm.close()
        }

        if (!AuthorizedUser.instance.isImageSent) {
            val token: String? = AuthorizedUser.instance.token
            val realm = Realm.getDefaultInstance()
            val userC = realm.where(User::class.java).equalTo("login", AuthorizedUser.instance.login).findFirst()
            val user = realm.copyFromRealm(userC!!)
            if (user != null) {
                val path = FileUtils.getPicturesDirectory(applicationContext)
                val file = File(path.plus("/").plus(user.image))
                Rx2AndroidNetworking.upload(ApiEndPoint.ENDPOINT_SERVER_USER_IMAGE_SEND)
                        .addHeaders(ApiHeader.ProtectedApiHeader(BuildConfig.API_KEY, AuthorizedUser.instance._id, AuthorizedUser.instance.token))
                        .addHeaders("Authorization", "bearer ".plus(token))
                        .addMultipartFile("image", file)
                        .addMultipartParameter("userLogin",user.login)
                        .addQueryParameter("XDEBUG_SESSION_START", "xdebug")
                        .build()
                        .getAsJSONObject(object : JSONObjectRequestListener {
                            override fun onResponse(response: JSONObject?) {
                                AuthorizedUser.instance.isImageSent = true
                            }
                            override fun onError(error: ANError) {
                            }
                        })
            }
            realm.close()
        }
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
        private const val TAG = "UserService"
    }
}
