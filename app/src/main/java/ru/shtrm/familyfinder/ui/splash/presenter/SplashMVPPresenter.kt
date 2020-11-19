package ru.shtrm.familyfinder.ui.splash.presenter

import ru.shtrm.familyfinder.ui.base.presenter.MVPPresenter
import ru.shtrm.familyfinder.ui.splash.interactor.SplashMVPInteractor
import ru.shtrm.familyfinder.ui.splash.view.SplashMVPView

/**
 * Created by jyotidubey on 04/01/18.
 */
interface SplashMVPPresenter<V : SplashMVPView, I : SplashMVPInteractor> : MVPPresenter<V, I>