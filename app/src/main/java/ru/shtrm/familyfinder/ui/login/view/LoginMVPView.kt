package ru.shtrm.familyfinder.ui.login.view

import ru.shtrm.familyfinder.ui.base.view.MVPView

interface LoginMVPView : MVPView {

    fun showValidationMessage(errorCode: Int)
    fun openMainActivity()
    fun openRegisterActivity()
    fun initAcra()

}