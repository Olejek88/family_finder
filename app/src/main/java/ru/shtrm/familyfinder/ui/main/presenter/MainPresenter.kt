package ru.shtrm.familyfinder.ui.main.presenter

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.widget.Toast
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

    override fun onDrawerOptionLogoutClick(context: Context) {
        interactor?.let {
            compositeDisposable.add(
                    it.makeLogoutApiCall()
                            .compose(schedulerProvider.ioToMainObservableScheduler())
                            .doOnError {
                                t: Throwable -> Log.e("performApiCall", t.message)
                                val toast = Toast.makeText(context, t.message, Toast.LENGTH_LONG)
                                toast.setGravity(Gravity.BOTTOM, 0, 0)
                                toast.show()
                            }
                            .subscribe({logoutResponse ->
                                if (logoutResponse.statusCode === "0") {
                                    interactor?.performUserLogout()
                                    getView()?.let {
                                        it.openLoginActivity()
                                    }
                                } else {
                                    val toast = Toast.makeText(context, "Logout error: "+logoutResponse.message, Toast.LENGTH_LONG)
                                    toast.setGravity(Gravity.BOTTOM, 0, 0)
                                    toast.show()
                                }
                            }, { err -> println(err) }))
        }

    }

    private fun getUserData() = interactor?.let {
        val userData = it.getUserDetails()
        getView()?.inflateUserDetails(userData)
    }
}