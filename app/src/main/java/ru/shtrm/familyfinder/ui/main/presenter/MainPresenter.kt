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
    }

    override fun onDrawerOptionAboutClick() = getView()?.openAboutFragment()

    override fun onDrawerOptionMapClick() = getView()?.openMapFragment()

    override fun onDrawerOptionProfileClick() = getView()?.openProfileFragment()

    override fun onDrawerOptionFamilyClick() = getView()?.openFamilyFragment()

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

    private fun getUserData() = interactor?.let {
        val userData = it.getUserDetails()
        getView()?.inflateUserDetails(userData)
    }

}