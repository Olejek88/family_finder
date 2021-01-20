package ru.shtrm.familyfinder.ui.register.presenter

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.widget.ProgressBar
import android.widget.Toast
import io.reactivex.disposables.CompositeDisposable
import io.realm.Realm
import ru.shtrm.familyfinder.data.database.AuthorizedUser
import ru.shtrm.familyfinder.data.database.repository.user.User
import ru.shtrm.familyfinder.data.network.RegisterResponse
import ru.shtrm.familyfinder.ui.base.presenter.BasePresenter
import ru.shtrm.familyfinder.ui.register.interactor.RegisterMVPInteractor
import ru.shtrm.familyfinder.ui.register.view.RegisterMVPView
import ru.shtrm.familyfinder.util.AppConstants
import ru.shtrm.familyfinder.util.SchedulerProvider
import java.util.*
import javax.inject.Inject


class RegisterPresenter<V : RegisterMVPView, I : RegisterMVPInteractor> @Inject internal constructor(interactor: I, schedulerProvider: SchedulerProvider, disposable: CompositeDisposable) : BasePresenter<V, I>(interactor = interactor, schedulerProvider = schedulerProvider, compositeDisposable = disposable), RegisterMVPPresenter<V, I> {

    override fun onServerRegisterClicked(email: String, password: String, username: String, context: Context, progressBar: ProgressBar) {
        when {
            email.isEmpty() -> getView()?.showValidationMessage(AppConstants.EMPTY_EMAIL_ERROR)
            password.isEmpty() -> getView()?.showValidationMessage(AppConstants.EMPTY_PASSWORD_ERROR)
            username.isEmpty() -> getView()?.showValidationMessage(AppConstants.EMPTY_NAME_ERROR)
            else -> {
                getView()?.showProgress(progressBar)
                interactor?.let {
                    compositeDisposable.add(
                            it.doServerRegisterApiCall(email, password, username)
                            .compose(schedulerProvider.ioToMainObservableScheduler())
                                    .doOnError {
                                        t: Throwable -> Log.e("performApiCall", t.message)
                                        getView()?.hideProgress(progressBar)
                                        val toast = Toast.makeText(context, t.message, Toast.LENGTH_LONG)
                                        toast.setGravity(Gravity.BOTTOM, 0, 0)
                                        toast.show()
                                    }
                            .subscribe({
                                registerResponse ->
                                if (registerResponse.statusCode === "0") {
                                    updateRegisterSharedPref(registerResponse = registerResponse,
                                            loggedInMode = AppConstants.LoggedInMode.LOGGED_IN_MODE_SERVER)
                                    getView()?.openMainActivity()
                                }
                                val toast = Toast.makeText(context, registerResponse.message, Toast.LENGTH_LONG)
                                toast.setGravity(Gravity.BOTTOM, 0, 0)
                                toast.show()
                                getView()?.hideProgress(progressBar)
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

        val authUser = AuthorizedUser.instance
        authUser.login = registerResponse.userEmail
        authUser.username = registerResponse.userName
        authUser.token = registerResponse.accessToken
        authUser._id = registerResponse.userId

        val realm = Realm.getDefaultInstance()
        val user = realm.where(User::class.java).equalTo("login", authUser.login).findFirst()
        if (user == null) {
            realm.executeTransactionAsync({ realmBg ->
                val userNew = realmBg.createObject<User>(User::class.java, User.getLastId())
                userNew.login = registerResponse.userEmail.toString()
                userNew.username = registerResponse.userName!!
                userNew.image = ""
                userNew.createdAt = Date()
                userNew.changedAt = Date()
            }, {
            }, { error ->
                Log.d("user", error.message)
            })
        }
        realm.close()
    }
}