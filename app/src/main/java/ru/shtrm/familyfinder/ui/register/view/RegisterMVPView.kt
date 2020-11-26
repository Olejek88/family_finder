package ru.shtrm.familyfinder.ui.register.view

import ru.shtrm.familyfinder.ui.base.view.MVPView

interface RegisterMVPView : MVPView {

    fun showValidationMessage(errorCode: Int)
    fun openMainActivity()
    fun openLoginActivity()
}