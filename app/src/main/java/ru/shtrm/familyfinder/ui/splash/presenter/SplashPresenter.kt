package ru.shtrm.familyfinder.ui.splash.presenter

import ru.shtrm.familyfinder.ui.base.presenter.BasePresenter
import ru.shtrm.familyfinder.ui.splash.interactor.SplashMVPInteractor
import ru.shtrm.familyfinder.ui.splash.view.SplashMVPView
import ru.shtrm.familyfinder.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by jyotidubey on 04/01/18.
 */
class SplashPresenter<V : SplashMVPView, I : SplashMVPInteractor> @Inject internal constructor(interactor: I, schedulerProvider: SchedulerProvider, disposable: CompositeDisposable) : BasePresenter<V, I>(interactor = interactor, schedulerProvider = schedulerProvider, compositeDisposable = disposable), SplashMVPPresenter<V, I> {

    override fun onAttach(view: V?) {
        super.onAttach(view)
        feedInDatabase()
    }

    private fun feedInDatabase() = interactor?.let {
        compositeDisposable.add(it.seedQuestions()
                .flatMap { interactor?.seedOptions() }
                .compose(schedulerProvider.ioToMainObservableScheduler())
                .subscribe({
                    getView()?.let { decideActivityToOpen() }
                }))
    }

    private fun decideActivityToOpen() = getView()?.let {
        if (isUserLoggedIn())
            it.openMainActivity()
        else
            it.openLoginActivity()
    }

    private fun isUserLoggedIn(): Boolean {
        interactor?.let { return it.isUserLoggedIn() }
        return false
    }

}