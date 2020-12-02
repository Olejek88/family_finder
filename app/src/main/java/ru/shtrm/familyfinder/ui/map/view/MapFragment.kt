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
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.fragment_map.*
import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.OverlayItem
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.compass.CompassOverlay
import ru.shtrm.familyfinder.BuildConfig
import ru.shtrm.familyfinder.R
import ru.shtrm.familyfinder.ui.base.view.BaseFragment
import ru.shtrm.familyfinder.ui.map.interactor.MapMVPInteractor
import ru.shtrm.familyfinder.ui.map.presenter.MapMVPPresenter
import java.util.*
import javax.inject.Inject

class MapFragment : BaseFragment(), MapMVPView {

    companion object {

        internal const val TAG = "MapFragment"
        fun newInstance(): MapFragment {
            return MapFragment()
        }
    }

    @Inject
    internal lateinit var presenter: MapMVPPresenter<MapMVPView, MapMVPInteractor>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_map, container, false)
        initMap(view.context)
        presenter.onAttach(this)
        return view
    }

    override fun setUp() = navBackBtn.setOnClickListener { getBaseActivity()?.onFragmentDetached(TAG) }

    private fun initMap(context: Context) {
        var location: Location?
        var curLatitude: Double = 0.toDouble()
        var curLongitude:Double = 0.toDouble()
        val lm = context.getSystemService(LOCATION_SERVICE) as LocationManager
        val permission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
        //val overlayItemArray: ArrayList<OverlayItem>
        val aOverlayItemArray: ArrayList<OverlayItem> = ArrayList()

        if (lm != null && permission == PackageManager.PERMISSION_GRANTED) {
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location == null) {
                location = getLastKnownLocation(context)
            }

            if (location != null) {
                curLatitude = location.latitude
                curLongitude = location.longitude
            }
        }

        OpenStreetMapTileProviderConstants.setUserAgentValue(BuildConfig.APPLICATION_ID)
        gps_mapview.setTileSource(TileSourceFactory.MAPNIK)
        gps_mapview.setBuiltInZoomControls(true)
        //MAPQUESTOSM
        val mapController = gps_mapview.controller
        mapController.setZoom(17)
        val point2 = GeoPoint(curLatitude, curLongitude)
        mapController.setCenter(point2)

        val overlayItem = OverlayItem("You are here", "YAH",
                GeoPoint(curLatitude, curLongitude))
        aOverlayItemArray.add(overlayItem)
        val aItemizedIconOverlay = ItemizedIconOverlay(context, aOverlayItemArray, null)
        gps_mapview.overlays.add(aItemizedIconOverlay)

        val waypoints = ArrayList<GeoPoint>()
        val currentPoint = GeoPoint(curLatitude, curLongitude)
        waypoints.add(currentPoint)

        val compassOverlay = CompassOverlay(context, gps_mapview)
        compassOverlay.enableCompass()
        gps_mapview.overlays.add(compassOverlay)
        /*
        MyLocationNewOverlay mLocationOverlay = new MyLocationNewOverlay(getActivity()
                .getApplicationContext(), mapView);
        mLocationOverlay.enableMyLocation();
        mapView.getOverlays().add(mLocationOverlay); */
        val mScaleBarOverlay = ScaleBarOverlay(gps_mapview)
        mScaleBarOverlay.setCentred(true)
        //play around with these values to get the location on screen in the right place for your applicatio
        mScaleBarOverlay.setScaleBarOffset(200, 10)
        gps_mapview.overlays.add(mScaleBarOverlay)

    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    private fun getLastKnownLocation(context: Context): Location? {
        var bestLocation: Location? = null

        val mLocationManager: LocationManager?
        mLocationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager?
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
