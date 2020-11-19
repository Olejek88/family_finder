package ru.shtrm.familyfinder.ui.splash.view

import ru.shtrm.familyfinder.ui.base.view.MVPView

/**
 * Created by jyotidubey on 04/01/18.
 */
interface SplashMVPView : MVPView {

    fun showSuccessToast()
    fun showErrorToast()
    fun openMainActivity()
    fun openLoginActivity()
}