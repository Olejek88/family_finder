package ru.shtrm.familyfinder.ui.login.presenter

import ru.shtrm.familyfinder.ui.base.presenter.MVPPresenter
import ru.shtrm.familyfinder.ui.login.interactor.LoginMVPInteractor
import ru.shtrm.familyfinder.ui.login.view.LoginMVPView

/**
 * Created by jyotidubey on 10/01/18.
 */
interface LoginMVPPresenter<V : LoginMVPView, I : LoginMVPInteractor> : MVPPresenter<V, I> {

    fun onServerLoginClicked(email: String, password: String)
    fun onFBLoginClicked()
    fun onGoogleLoginClicked()

}