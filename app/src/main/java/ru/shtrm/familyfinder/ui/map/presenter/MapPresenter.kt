package ru.shtrm.familyfinder.ui.map.presenter

import io.reactivex.disposables.CompositeDisposable
import ru.shtrm.familyfinder.ui.base.presenter.BasePresenter
import ru.shtrm.familyfinder.ui.map.interactor.MapMVPInteractor
import ru.shtrm.familyfinder.ui.map.view.MapMVPView
import ru.shtrm.familyfinder.util.SchedulerProvider
import javax.inject.Inject

class MapPresenter<V : MapMVPView, I : MapMVPInteractor> @Inject internal constructor(interactor: I, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable) : BasePresenter<V, I>(interactor = interactor, schedulerProvider = schedulerProvider, compositeDisposable = compositeDisposable), MapMVPPresenter<V, I> {
}