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
import io.realm.RealmChangeListener
import kotlinx.android.synthetic.main.fragment_map.view.*
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
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

        OpenStreetMapTileProviderConstants.setUserAgentValue(BuildConfig.APPLICATION_ID)
        view.gps_mapview.setTileSource(TileSourceFactory.MAPNIK)
        view.gps_mapview.setBuiltInZoomControls(true)

        val mapController = view.gps_mapview.controller
        mapController.setZoom(17)

        val realm = Realm.getDefaultInstance()
        val users = realm.where(User::class.java).findAll()
        for (user in users) {
            val marker = formMarker(user, view.gps_mapview)
            view.gps_mapview.overlays.add(marker)

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

            if (user.login==AuthorizedUser.instance.login) {
                val point2 = GeoPoint(user.lastLatitude, user.lastLongitude)
                mapController.setCenter(point2)
            }

            user.addChangeListener(RealmChangeListener { userC ->
                view.gps_mapview.overlays.remove(marker)
                formMarker(userC as User, view.gps_mapview)
                view.gps_mapview.invalidate()
            })
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

    private fun formMarker(user: User, mapView: MapView): Marker {
        val infoWindow = UserInfoWindow(R.layout.bubble, mapView, user)
        val marker = Marker(mapView)
        marker.infoWindow = infoWindow
        marker.position = GeoPoint(user.lastLatitude, user.lastLongitude)
        marker.setIcon(mapView.context.getDrawable(R.drawable.location))
        marker.setAnchor(Marker.ANCHOR_CENTER, 1.0f)
        marker.infoWindow = infoWindow
        marker.title = user.username

        marker.setOnMarkerClickListener { click_marker, _ ->
            InfoWindow.closeAllInfoWindowsOn(mapView)
            click_marker.showInfoWindow()
            mapView.controller.animateTo(marker.position)
            true
        }
        return marker
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
