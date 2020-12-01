package ru.shtrm.familyfinder.ui.main.presenter

import ru.shtrm.familyfinder.ui.base.presenter.MVPPresenter
import ru.shtrm.familyfinder.ui.main.interactor.MainMVPInteractor
import ru.shtrm.familyfinder.ui.main.view.MainMVPView

interface MainMVPPresenter<V : MainMVPView, I : MainMVPInteractor> : MVPPresenter<V, I> {

    fun onDrawerOptionAboutClick(): Unit?
    fun onDrawerOptionRateUsClick(): Unit?
    fun onDrawerOptionFeedClick(): Unit?
    fun onDrawerOptionLogoutClick()

}