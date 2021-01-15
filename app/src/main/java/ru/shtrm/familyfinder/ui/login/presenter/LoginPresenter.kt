package ru.shtrm.familyfinder.ui.login.presenter

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.widget.Toast
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
import java.util.*
import javax.inject.Inject

class LoginPresenter<V : LoginMVPView, I : LoginMVPInteractor> @Inject internal constructor(interactor: I, schedulerProvider: SchedulerProvider, disposable: CompositeDisposable) : BasePresenter<V, I>(interactor = interactor, schedulerProvider = schedulerProvider, compositeDisposable = disposable), LoginMVPPresenter<V, I> {

    override fun onServerLoginClicked(email: String, password: String, context: Context) {
        when {
            email.isEmpty() -> getView()?.showValidationMessage(AppConstants.EMPTY_EMAIL_ERROR)
            password.isEmpty() -> getView()?.showValidationMessage(AppConstants.EMPTY_PASSWORD_ERROR)
            else -> {
                getView()?.showProgress()
                interactor?.let {
                    compositeDisposable.add(it.doServerLoginApiCall(email, password)
                            .compose(schedulerProvider.ioToMainObservableScheduler())
                            .doOnError { t: Throwable ->
                                Log.e("performApiCall", t.message)
                                getView()?.hideProgress()
                                val toast = Toast.makeText(context, t.message, Toast.LENGTH_LONG)
                                toast.setGravity(Gravity.BOTTOM, 0, 0)
                                toast.show()
                            }
                            .subscribe({ loginResponse ->
                                if (loginResponse.statusCode == "0") {
                                    updateUserInSharedPref(loginResponse = loginResponse,
                                            loggedInMode = AppConstants.LoggedInMode.LOGGED_IN_MODE_SERVER)
                                    getView()?.openMainActivity()
                                }
                                val toast = Toast.makeText(context, loginResponse.message, Toast.LENGTH_LONG)
                                toast.setGravity(Gravity.BOTTOM, 0, 0)
                                toast.show()
                                getView()?.hideProgress()
                                sendTokenRequest(loginResponse.userEmail!!)
                            }, { err -> println(err) }))
                }
            }
        }
    }

    override fun sendTokenRequest(userLogin: String) {
        interactor?.let {
            compositeDisposable.add(it.makeTokenApiCall(userLogin)
                    .compose(schedulerProvider.ioToMainObservableScheduler())
                    .doOnError { t: Throwable ->
                        Log.e("performApiCall", t.message)
                        sendTokenRequest(userLogin)
                    }
                    .subscribe({ tokenResponse ->
                        if (tokenResponse.statusCode == "0") {
                            Log.e("token", tokenResponse.token)
                            val authUser = AuthorizedUser.instance;
                            authUser.token = tokenResponse.token
                            if (!AuthorizedUser.instance.isAcraInit) {
                                getView()!!.initAcra()
                                AuthorizedUser.instance.isAcraInit = true
                            }
                        } else {
                            sendTokenRequest(userLogin)
                        }
                    }, { err -> println(err) }))
            }
    }

    override fun checkUserLogin(): Boolean {
        val authUser = AuthorizedUser.instance
        val realm = Realm.getDefaultInstance()
        val userLogin = interactor?.getUserLogin()
        val user: User?
        if (userLogin!="")
            user = realm.where(User::class.java).equalTo("login",interactor?.getUserLogin()).findFirst()
        else
            user = realm.where(User::class.java).findFirst()
        if (user != null) {
            if (user.changedAt.before(Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 7))))
                return false
            authUser.login = user.login
            authUser.username = user.username
            authUser.token = ""
            authUser._id = user._id
            authUser.image = user.image
            authUser.location = user.location
            authUser.isSent = user.isSent

            sendTokenRequest(user.login)
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

        val realm = Realm.getDefaultInstance()
        val user = realm.where(User::class.java).equalTo("login", authUser.login).findFirst()
        if (user == null) {
            realm.executeTransactionAsync { realmBg ->
                val userNew = realmBg.createObject<User>(User::class.java, User.getLastId())
                userNew.login = loginResponse.userEmail.toString()
                userNew.username = loginResponse.userName!!
                userNew.image = ""
            }
        } else {
            realm.executeTransaction {
                user.login = loginResponse.userEmail.toString()
                user.username = loginResponse.userName!!
                user.changedAt = Date()
                //user._id = loginResponse.userId!!
                //user.image = loginResponse.serverProfilePicUrl!!
                interactor?.updateUserInSharedPrefAfterLogin(user)
            }
        }
        realm.close()
    }
}