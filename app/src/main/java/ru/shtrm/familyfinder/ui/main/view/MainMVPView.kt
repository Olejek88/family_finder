package ru.shtrm.familyfinder.ui.main.view

import android.graphics.Bitmap
import ru.shtrm.familyfinder.ui.base.view.MVPView

interface MainMVPView : MVPView {

    fun inflateUserDetails(userDetails: Pair<String?, String?>)
    fun openLoginActivity()
    fun openAboutFragment()
    fun openProfileFragment()
    fun setDrawerBitmap(userBitmap : Bitmap)
    fun openMapFragment()
    fun openFamilyFragment()
    fun lockDrawer(): Unit?
    fun unlockDrawer(): Unit?
}