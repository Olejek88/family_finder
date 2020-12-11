package ru.shtrm.familyfinder.gps

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.util.Log
import io.realm.Realm
import ru.shtrm.familyfinder.data.database.AuthorizedUser
import ru.shtrm.familyfinder.data.database.repository.route.Route
import java.lang.Math.abs
import java.util.*

class GPSListener : LocationListener {

    private var prevLocation: Location? = null

    override fun onLocationChanged(location: Location?) {
        if (prevLocation != null) {
            if (abs(prevLocation!!.latitude - location!!.latitude) > 0.001 || abs(prevLocation!!.longitude - location.longitude) > 0.001)
                recordGPSData(location.latitude, location.longitude)
        }
        if (location != null) {
            prevLocation = location
        }
    }

    override fun onProviderDisabled(arg0: String) {}

    override fun onProviderEnabled(arg0: String) {}

    override fun onStatusChanged(provider: String, status: Int, boundle: Bundle) {}

    private fun recordGPSData(Latitude: Double?, Longitude: Double?) {
        val user = AuthorizedUser.instance
        if (user._id == 0.toLong()) {
            Log.d("route", "no user found")
            return
        }
        // примерно от Европы и до северных морей
        if (Latitude != null && Longitude != null) {
            if (Latitude > 1.0 && Longitude > 1.0) {
                val realm = Realm.getDefaultInstance()
                realm.executeTransactionAsync({ realmBg ->
                    val route_new = realmBg.createObject<Route>(Route::class.java)
                    route_new.userId = user._id!!
                    route_new.latitude = Latitude
                    route_new.longitude = Longitude
                    Log.d("route", "L:".plus(Latitude).plus(" L:").plus(Longitude))
                    route_new.isSent = false
                    route_new.date = Date()
                }, {
                }, { error ->
                    Log.d("route", error.message)
                })
            }
        }
    }
}