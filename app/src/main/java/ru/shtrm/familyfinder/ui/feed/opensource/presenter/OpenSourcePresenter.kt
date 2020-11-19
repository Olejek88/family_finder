package ru.shtrm.familyfinder.ui.feed.opensource.presenter

import ru.shtrm.familyfinder.ui.base.presenter.BasePresenter
import ru.shtrm.familyfinder.ui.feed.opensource.interactor.OpenSourceMVPInteractor
import ru.shtrm.familyfinder.ui.feed.opensource.view.OpenSourceMVPView
import ru.shtrm.familyfinder.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by jyotidubey on 14/01/18.
 */
class OpenSourcePresenter<V : OpenSourceMVPView, I : OpenSourceMVPInteractor> @Inject constructor(interactor: I, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable) : BasePresenter<V, I>(interactor = interactor, schedulerProvider = schedulerProvider, compositeDisposable = compositeDisposable), OpenSourceMVPPresenter<V, I> {

    override fun onViewPrepared() {
        getView()?.showProgress()
        interactor?.let {
            compositeDisposable.add(it.getOpenSourceList()
                    .compose(schedulerProvider.ioToMainObservableScheduler())
                    .subscribe { openSourceResponse ->
                        getView()?.let {
                            it.hideProgress()
                            it.displayOpenSourceList(openSourceResponse.data)
                        }
                    })
        }

    }
}