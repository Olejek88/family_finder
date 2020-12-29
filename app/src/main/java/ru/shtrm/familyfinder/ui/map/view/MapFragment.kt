package ru.shtrm.familyfinder.ui.map.view

import android.Manifest
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_map.view.*
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.infowindow.InfoWindow
import ru.shtrm.familyfinder.BuildConfig
import ru.shtrm.familyfinder.R
import ru.shtrm.familyfinder.data.database.AuthorizedUser
import ru.shtrm.familyfinder.data.database.repository.user.User
import ru.shtrm.familyfinder.ui.base.view.BaseFragment
import java.util.*


class MapFragment : BaseFragment(), MapMVPView {

    companion object {

        internal const val TAG = "MapFragment"
        fun newInstance(): MapFragment {
            return MapFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_map, container, false)
        initMap(view.context, view)
        //presenter.onAttach(this)
        return view
    }

    override fun setUp() {}

    private fun initMap(context: Context, view: View) {
        var curLatitude: Double
        var curLongitude: Double

        OpenStreetMapTileProviderConstants.setUserAgentValue(BuildConfig.APPLICATION_ID)
        view.gps_mapview.setTileSource(TileSourceFactory.MAPNIK)
        view.gps_mapview.setBuiltInZoomControls(true)

        val mapController = view.gps_mapview.controller
        mapController.setZoom(17)

        val waypoints = ArrayList<GeoPoint>()

        val realm = Realm.getDefaultInstance()
        val users = realm.where(User::class.java).findAll()
        for (user in users) {
            curLatitude = user.lastLatitude
            curLongitude = user.lastLongitude
            val endPoint = GeoPoint(curLatitude, curLongitude)
            waypoints.add(endPoint)

            val infoWindow = UserInfoWindow(R.layout.bubble, view.gps_mapview, user)
            val marker = Marker(view.gps_mapview)
            marker.infoWindow = infoWindow
            marker.position = endPoint
            marker.setIcon(context.getDrawable(R.drawable.location))
            marker.setAnchor(Marker.ANCHOR_CENTER, 1.0f)
            marker.infoWindow = infoWindow
            view.gps_mapview.overlays.add(marker)
            marker.title = user.username

            val mReceive = object : MapEventsReceiver {
                override fun singleTapConfirmedHelper(p: GeoPoint): Boolean {
                    InfoWindow.closeAllInfoWindowsOn(view.gps_mapview)
                    return false
                }
                override fun longPressHelper(p: GeoPoint): Boolean {
                    return false
                }
            }
            view.gps_mapview.overlays.add(MapEventsOverlay(mReceive))

            marker.setOnMarkerClickListener { click_marker, mapView ->
                InfoWindow.closeAllInfoWindowsOn(view.gps_mapview)
                click_marker.showInfoWindow()
                mapView.controller.animateTo(marker.position)
                true
            }

            if (user.login==AuthorizedUser.instance.login) {
                val point2 = GeoPoint(curLatitude, curLongitude)
                mapController.setCenter(point2)
            }
        }

        val compassOverlay = CompassOverlay(context, view.gps_mapview)
        compassOverlay.enableCompass()
        view.gps_mapview.overlays.add(compassOverlay)
        val mScaleBarOverlay = ScaleBarOverlay(view.gps_mapview)
        mScaleBarOverlay.setCentred(true)
        //play around with these values to get the location on screen in the right place for your applicatio
        mScaleBarOverlay.setScaleBarOffset(200, 10)
        view.gps_mapview.overlays.add(mScaleBarOverlay)
        realm.close()
    }

    private fun getLastKnownLocation(context: Context): Location? {
        var bestLocation: Location? = null

        val mLocationManager: LocationManager? = context.getSystemService(LOCATION_SERVICE) as LocationManager?
        val permission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (mLocationManager != null && permission == PackageManager.PERMISSION_GRANTED) {
            val providers = mLocationManager.getProviders(true)
            for (provider in providers) {
                val l = mLocationManager.getLastKnownLocation(provider) ?: continue
                if (bestLocation == null || l.accuracy < bestLocation.accuracy) {
                    bestLocation = l
                }
            }
        }

        return bestLocation
    }
}
