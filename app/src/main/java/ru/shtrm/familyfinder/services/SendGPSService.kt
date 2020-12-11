package ru.shtrm.familyfinder.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import io.realm.Realm
import ru.shtrm.familyfinder.BuildConfig
import ru.shtrm.familyfinder.data.database.AuthorizedUser
import ru.shtrm.familyfinder.data.database.repository.route.Route
import ru.shtrm.familyfinder.data.network.ApiHeader
import ru.shtrm.familyfinder.data.network.AppApiHelper
import ru.shtrm.familyfinder.data.network.SendRoutesRequest

class SendGPSService : Service() {
    private var isRuning: Boolean = false
    private var apiHelper: AppApiHelper? = null

    private val task = Runnable {
        val realm = Realm.getDefaultInstance()

        val user = AuthorizedUser.instance
        if (user._id == 0.toLong()) {
        }

        val items = realm.copyFromRealm(realm.where(Route::class.java).equalTo("isSent", false).limit(10).findAll())
        val routes = arrayListOf<Route>()
        for (i in items.indices) {
            val route = items[i]
            routes.add(route as Route)
        }

        apiHelper = AppApiHelper(ApiHeader(ApiHeader.PublicApiHeader(BuildConfig.API_KEY),ApiHeader.ProtectedApiHeader(BuildConfig.API_KEY,user._id,user.token)))
        apiHelper!!.performSendRoutes(SendRoutesRequest.SendRoutesRequest(id = user._id!!, routes = routes),"bearer ".plus(user.token))
                .doOnError { t: Throwable ->
                    Log.e("performApiCall", t.message)
                }
                .subscribe({ sendResponse ->
                    if (sendResponse.statusCode === "0") {
                        realm.beginTransaction()
                        //realm.where(Route::class.java).equalTo("sent", false).findAll().deleteAllFromRealm()
                        realm.where(Route::class.java).equalTo("sent", false).findAll().setBoolean("sent", true)
                        realm.commitTransaction()
                    }
                }, { err -> println(err) })
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
