package ru.shtrm.familyfinder.ui.main.view

import ru.shtrm.familyfinder.ui.base.view.MVPView

interface MainMVPView : MVPView {

    fun inflateUserDetails(userDetails: Pair<String?, String?>)
    fun openLoginActivity()
    fun openFeedActivity()
    fun openAboutFragment()
    fun openProfileFragment()
    fun openRateUsDialog(): Unit?
    fun lockDrawer(): Unit?
    fun unlockDrawer(): Unit?
}