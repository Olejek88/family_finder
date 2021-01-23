package ru.shtrm.familyfinder.ui.login.presenter

import android.content.Context
import android.widget.ProgressBar
import ru.shtrm.familyfinder.ui.base.presenter.MVPPresenter
import ru.shtrm.familyfinder.ui.login.interactor.LoginMVPInteractor
import ru.shtrm.familyfinder.ui.login.view.LoginMVPView

interface LoginMVPPresenter<V : LoginMVPView, I : LoginMVPInteractor> : MVPPresenter<V, I> {

    fun onServerLoginClicked(email: String, password: String, context: Context, progressBar: ProgressBar)
    fun onServerRegisterClicked(): Unit?
    fun getUserName(): String?
    fun checkUserLogin(): Boolean
}