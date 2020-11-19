package ru.shtrm.familyfinder.ui.feed.blog.presenter

import ru.shtrm.familyfinder.ui.base.presenter.MVPPresenter
import ru.shtrm.familyfinder.ui.feed.blog.interactor.BlogMVPInteractor
import ru.shtrm.familyfinder.ui.feed.blog.view.BlogMVPView

/**
 * Created by jyotidubey on 13/01/18.
 */
interface BlogMVPPresenter<V : BlogMVPView, I : BlogMVPInteractor> : MVPPresenter<V, I> {

    fun onViewPrepared()
}