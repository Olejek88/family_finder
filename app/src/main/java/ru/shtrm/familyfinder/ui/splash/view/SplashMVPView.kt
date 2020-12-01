package ru.shtrm.familyfinder.ui.splash.view

import ru.shtrm.familyfinder.ui.base.view.MVPView

interface SplashMVPView : MVPView {

    fun showSuccessToast()
    fun showErrorToast()
    fun openMainActivity()
    fun openLoginActivity()
}