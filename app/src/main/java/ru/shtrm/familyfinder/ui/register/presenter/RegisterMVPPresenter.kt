package ru.shtrm.familyfinder.ui.register.presenter

import ru.shtrm.familyfinder.ui.base.presenter.MVPPresenter
import ru.shtrm.familyfinder.ui.register.interactor.RegisterMVPInteractor
import ru.shtrm.familyfinder.ui.register.view.RegisterMVPView

interface RegisterMVPPresenter<V : RegisterMVPView, I : RegisterMVPInteractor> : MVPPresenter<V, I> {

    fun onServerRegisterClicked(email: String, password: String, username: String)
    fun onServerLoginClicked(): Unit?

}