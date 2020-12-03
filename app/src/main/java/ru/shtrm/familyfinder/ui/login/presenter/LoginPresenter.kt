package ru.shtrm.familyfinder.ui.login.presenter

import io.reactivex.disposables.CompositeDisposable
import io.realm.Realm
import ru.shtrm.familyfinder.data.database.AuthorizedUser
import ru.shtrm.familyfinder.data.database.repository.user.User
import ru.shtrm.familyfinder.data.network.LoginResponse
import ru.shtrm.familyfinder.ui.base.presenter.BasePresenter
import ru.shtrm.familyfinder.ui.login.interactor.LoginMVPInteractor
import ru.shtrm.familyfinder.ui.login.view.LoginMVPView
import ru.shtrm.familyfinder.util.AppConstants
import ru.shtrm.familyfinder.util.SchedulerProvider
import javax.inject.Inject

class LoginPresenter<V : LoginMVPView, I : LoginMVPInteractor> @Inject internal constructor(interactor: I, schedulerProvider: SchedulerProvider, disposable: CompositeDisposable) : BasePresenter<V, I>(interactor = interactor, schedulerProvider = schedulerProvider, compositeDisposable = disposable), LoginMVPPresenter<V, I> {

    override fun onServerLoginClicked(email: String, password: String) {
        when {
            email.isEmpty() -> getView()?.showValidationMessage(AppConstants.EMPTY_EMAIL_ERROR)
            password.isEmpty() -> getView()?.showValidationMessage(AppConstants.EMPTY_PASSWORD_ERROR)
            else -> {
                getView()?.showProgress()
                interactor?.let {
                    compositeDisposable.add(it.doServerLoginApiCall(email, password)
                            .compose(schedulerProvider.ioToMainObservableScheduler())
                            .subscribe({ loginResponse ->
                                updateUserInSharedPref(loginResponse = loginResponse,
                                        loggedInMode = AppConstants.LoggedInMode.LOGGED_IN_MODE_SERVER)
                                getView()?.openMainActivity()
                            }, { err -> println(err) }))
                }

            }
        }
    }

    override fun checkUserLogin(): Boolean {
        val authUser = AuthorizedUser.instance;
        val realm = Realm.getDefaultInstance()
        val user = realm.where(User::class.java).findFirst()
        if (user != null) {
            authUser.login = user.login
            authUser.username = user.username
            authUser.token = ""
            authUser._id = user._id
            authUser.image = user.image
            return true
        }
        return false
    }

    override fun getUserName(): String? {
        return interactor?.getUserName()
    }

    override fun onServerRegisterClicked() = getView()?.openRegisterActivity()

    private fun updateUserInSharedPref(loginResponse: LoginResponse,
                                       loggedInMode: AppConstants.LoggedInMode) {
        val authUser = AuthorizedUser.instance;
        authUser.login = loginResponse.userEmail
        authUser.username = loginResponse.userName
        authUser.token = loginResponse.accessToken
        authUser._id = loginResponse.userId
        authUser.image = loginResponse.serverProfilePicUrl
        interactor?.updateUserInSharedPref(loginResponse, loggedInMode)

        val realm = Realm.getDefaultInstance()
        val user = realm.where(User::class.java).equalTo("login", authUser.login).findFirst()
        if (user == null) {
            realm.executeTransaction {
                val user_new = realm.createObject(User::class.java)
                user_new.login = loginResponse.userEmail.toString()
                user_new.username = loginResponse.userName!!
                user_new._id = loginResponse.userId!!
                user_new.image = loginResponse.serverProfilePicUrl!!
            }
        } else {
            realm.executeTransaction {
                user.login = loginResponse.userEmail.toString()
                user.username = loginResponse.userName!!
                user._id = loginResponse.userId!!
                user.image = loginResponse.serverProfilePicUrl!!
            }
        }
        realm.close()
        interactor?.updateUserInSharedPref(loginResponse, loggedInMode)
    }
}