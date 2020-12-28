package ru.shtrm.familyfinder.ui.map.view

import android.util.Log
import kotlinx.android.synthetic.main.bubble.view.*
import org.osmdroid.api.IMapView
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.infowindow.InfoWindow
import ru.shtrm.familyfinder.data.database.repository.user.User
import ru.shtrm.familyfinder.util.FileUtils

class UserInfoWindow(layoutResId: Int, mapView: MapView, user: User) : InfoWindow(layoutResId, mapView) {
    private var user: User? = null

    init {
        this.user = user
    }

    override fun onOpen(item: Any) {
        if (mView == null) {
            Log.w(IMapView.LOGTAG, "Error trapped, BasicInfoWindow.open, mView is null!")
            return
        }
        mView.bubble_user_name.text = user!!.username;
        mView.bubble_user_distance.text = user!!.location;
        val path = FileUtils.getPicturesDirectory(mView.context)
        val avatar = user!!.image
        if (avatar != "") {
            mView.bubble_user_image.setImageBitmap(FileUtils.getBitmapByPath(path, avatar))
        }
    }

    override fun onClose() {
        //by default, do nothing
    }

}
