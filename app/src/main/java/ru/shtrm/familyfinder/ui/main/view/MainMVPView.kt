package ru.shtrm.familyfinder.ui.main.view

import ru.shtrm.familyfinder.ui.base.view.MVPView

interface MainMVPView : MVPView {

    fun inflateUserDetails(userDetails: Pair<String?, String?>)
    fun openLoginActivity()
    fun openAboutFragment()
    fun openProfileFragment()
    fun openMapFragment()
    fun openFamilyFragment()
    fun lockDrawer(): Unit?
    fun unlockDrawer(): Unit?
}