package ru.shtrm.familyfinder.ui.register.presenter

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import io.reactivex.disposables.CompositeDisposable
import ru.shtrm.familyfinder.data.network.RegisterResponse
import ru.shtrm.familyfinder.ui.base.presenter.BasePresenter
import ru.shtrm.familyfinder.ui.register.interactor.RegisterMVPInteractor
import ru.shtrm.familyfinder.ui.register.view.RegisterMVPView
import ru.shtrm.familyfinder.util.AppConstants
import ru.shtrm.familyfinder.util.SchedulerProvider
import javax.inject.Inject



class RegisterPresenter<V : RegisterMVPView, I : RegisterMVPInteractor> @Inject internal constructor(interactor: I, schedulerProvider: SchedulerProvider, disposable: CompositeDisposable) : BasePresenter<V, I>(interactor = interactor, schedulerProvider = schedulerProvider, compositeDisposable = disposable), RegisterMVPPresenter<V, I> {

    override fun onServerRegisterClicked(email: String, password: String, username: String, context: Context) {
        when {
            email.isEmpty() -> getView()?.showValidationMessage(AppConstants.EMPTY_EMAIL_ERROR)
            password.isEmpty() -> getView()?.showValidationMessage(AppConstants.EMPTY_PASSWORD_ERROR)
            username.isEmpty() -> getView()?.showValidationMessage(AppConstants.EMPTY_NAME_ERROR)
            else -> {
                getView()?.showProgress()
                interactor?.let {
                    compositeDisposable.add(
                            it.doServerRegisterApiCall(email, password, username)
                            .compose(schedulerProvider.ioToMainObservableScheduler())
                                    .doOnError {
                                        t: Throwable -> Log.e("performApiCall", t.message)
                                        getView()?.hideProgress()
                                        val toast = Toast.makeText(context, t.message, Toast.LENGTH_LONG)
                                        toast.setGravity(Gravity.BOTTOM, 0, 0)
                                        toast.show()
                                    }
                            .subscribe({
                                registerResponse -> updateRegisterSharedPref(registerResponse = registerResponse,
                                loggedInMode = AppConstants.LoggedInMode.LOGGED_IN_MODE_SERVER)
                                if (registerResponse.statusCode === "0") getView()?.openMainActivity()
                                val toast = Toast.makeText(context, registerResponse.message, Toast.LENGTH_LONG)
                                toast.setGravity(Gravity.BOTTOM, 0, 0)
                                toast.show()
                            }, { err -> println(err) }))
                }

            }
        }
    }

    override fun onServerLoginClicked() = getView()?.openLoginActivity()

    private fun updateRegisterSharedPref(registerResponse: RegisterResponse,
                                         loggedInMode: AppConstants.LoggedInMode) {
        Log.d("rest",registerResponse.statusCode)
        interactor?.updateRegisterSharedPref(registerResponse, loggedInMode)
    }

}