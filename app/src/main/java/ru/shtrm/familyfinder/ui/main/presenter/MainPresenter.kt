package ru.shtrm.familyfinder.ui.main.presenter

import io.reactivex.disposables.CompositeDisposable
import ru.shtrm.familyfinder.ui.base.presenter.BasePresenter
import ru.shtrm.familyfinder.ui.main.interactor.MainMVPInteractor
import ru.shtrm.familyfinder.ui.main.view.MainMVPView
import ru.shtrm.familyfinder.util.SchedulerProvider
import javax.inject.Inject

class MainPresenter<V : MainMVPView, I : MainMVPInteractor> @Inject internal constructor(interactor: I, schedulerProvider: SchedulerProvider, disposable: CompositeDisposable) : BasePresenter<V, I>(interactor = interactor, schedulerProvider = schedulerProvider, compositeDisposable = disposable), MainMVPPresenter<V, I> {

    override fun onAttach(view: V?) {
        super.onAttach(view)
        getUserData()
        getQuestionCards()
    }

    override fun refreshQuestionCards() = getQuestionCards()

    override fun onDrawerOptionRateUsClick() = getView()?.openRateUsDialog()

    override fun onDrawerOptionFeedClick() = getView()?.openFeedActivity()

    override fun onDrawerOptionAboutClick() = getView()?.openAboutFragment()

    override fun onDrawerOptionLogoutClick() {
        getView()?.showProgress()
        interactor?.let {
            compositeDisposable.add(
                    it.makeLogoutApiCall()
                            .compose(schedulerProvider.ioToMainObservableScheduler())
                            .subscribe({
                                interactor?.performUserLogout()
                                getView()?.let {
                                    it.hideProgress()
                                    it.openLoginActivity()
                                }
                            }, { err -> println(err) }))
        }

    }

    private fun getQuestionCards() = interactor?.let {
        compositeDisposable.add(it.getQuestionCardData()
                .compose(schedulerProvider.ioToMainSingleScheduler())
                .subscribe({ questionCard ->
                    getView()?.let {
                        if (questionCard.isEmpty()) return@subscribe
                        else it.displayQuestionCard(questionCard)
                    }
                }, { err -> println(err) }))
    }


    private fun getUserData() = interactor?.let {
        val userData = it.getUserDetails()
        getView()?.inflateUserDetails(userData)
    }

}