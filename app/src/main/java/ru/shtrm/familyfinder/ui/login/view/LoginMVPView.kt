package ru.shtrm.familyfinder.ui.login.view

import ru.shtrm.familyfinder.ui.base.view.MVPView

/**
 * Created by jyotidubey on 10/01/18.
 */
interface LoginMVPView : MVPView {

    fun showValidationMessage(errorCode: Int)
    fun openMainActivity()
}