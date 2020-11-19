package ru.shtrm.familyfinder.ui.feed.opensource.presenter

import ru.shtrm.familyfinder.ui.base.presenter.MVPPresenter
import ru.shtrm.familyfinder.ui.feed.opensource.interactor.OpenSourceMVPInteractor
import ru.shtrm.familyfinder.ui.feed.opensource.view.OpenSourceMVPView

/**
 * Created by jyotidubey on 14/01/18.
 */
interface OpenSourceMVPPresenter<V : OpenSourceMVPView, I : OpenSourceMVPInteractor> : MVPPresenter<V, I> {

    fun onViewPrepared()
}