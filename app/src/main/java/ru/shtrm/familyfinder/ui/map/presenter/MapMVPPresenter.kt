package ru.shtrm.familyfinder.ui.map.presenter

import ru.shtrm.familyfinder.ui.base.presenter.MVPPresenter
import ru.shtrm.familyfinder.ui.map.interactor.MapMVPInteractor
import ru.shtrm.familyfinder.ui.map.view.MapMVPView

interface MapMVPPresenter<V : MapMVPView, I : MapMVPInteractor> : MVPPresenter<V, I>