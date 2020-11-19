package ru.shtrm.familyfinder.ui.main.presenter

import ru.shtrm.familyfinder.ui.base.presenter.MVPPresenter
import ru.shtrm.familyfinder.ui.main.interactor.MainMVPInteractor
import ru.shtrm.familyfinder.ui.main.view.MainMVPView

/**
 * Created by jyotidubey on 08/01/18.
 */
interface MainMVPPresenter<V : MainMVPView, I : MainMVPInteractor> : MVPPresenter<V, I> {

    fun refreshQuestionCards(): Boolean?
    fun onDrawerOptionAboutClick(): Unit?
    fun onDrawerOptionRateUsClick(): Unit?
    fun onDrawerOptionFeedClick(): Unit?
    fun onDrawerOptionLogoutClick()

}