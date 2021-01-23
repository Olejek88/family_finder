package ru.shtrm.familyfinder.ui.login.presenter

import android.content.Context
import android.view.Gravity
import android.widget.ProgressBar
import android.widget.Toast
import io.reactivex.disposables.CompositeDisposable
import ru.shtrm.familyfinder.data.database.AuthorizedUser
import ru.shtrm.familyfinder.ui.base.presenter.BasePresenter
import ru.shtrm.familyfinder.ui.login.interactor.LoginMVPInteractor
import ru.shtrm.familyfinder.ui.login.view.LoginMVPView
import ru.shtrm.familyfinder.util.AppConstants
import ru.shtrm.familyfinder.util.SchedulerProvider
import javax.inject.Inject

class LoginPresenter<V : LoginMVPView, I : LoginMVPInteractor> @Inject internal constructor(interactor: I, schedulerProvider: SchedulerProvider, disposable: CompositeDisposable) : BasePresenter<V, I>(interactor = interactor, schedulerProvider = schedulerProvider, compositeDisposable = disposable), LoginMVPPresenter<V, I> {

    override fun onServerLoginClicked(email: String, password: String, context: Context, progressBar : ProgressBar) {
        when {
            email.isEmpty() -> getView()?.showValidationMessage(AppConstants.EMPTY_EMAIL_ERROR)
            password.isEmpty() -> getView()?.showValidationMessage(AppConstants.EMPTY_PASSWORD_ERROR)
            else -> {
                getView()?.showProgress(progressBar)
                interactor?.let {
                    compositeDisposable.add(it.doServerLoginApiCall(email, password)
                            .compose(schedulerProvider.ioToMainObservableScheduler())
                            .doOnError { t: Throwable ->
                                getView()?.hideProgress(progressBar)
                                val toast = Toast.makeText(context, t.message, Toast.LENGTH_LONG)
                                toast.setGravity(Gravity.BOTTOM, 0, 0)
                                toast.show()
                            }
                            .subscribe({ loginResponse ->
                                if (loginResponse.statusCode == "0") {
                                    it.updateUserInSharedPref(loginResponse = loginResponse,
                                            loggedInMode = AppConstants.LoggedInMode.LOGGED_IN_MODE_SERVER)
                                    getView()?.openMainActivity()
                                }
                                val toast = Toast.makeText(context, loginResponse.message, Toast.LENGTH_LONG)
                                toast.setGravity(Gravity.BOTTOM, 0, 0)
                                toast.show()
                                getView()?.hideProgress(progressBar)
                                it.sendTokenRequest(loginResponse.userEmail!!)
                            }, { err -> println(err) }))
                }
            }
        }
    }

    override fun getUserName(): String? {
        return interactor?.getUserName()
    }

    override fun onServerRegisterClicked() = getView()?.openRegisterActivity()

    override fun checkUserLogin(): Boolean {
        val authUser = AuthorizedUser.instance
        return interactor!!.checkUserLogin(authUser.login!!)
    }
}