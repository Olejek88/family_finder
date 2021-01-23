package ru.shtrm.familyfinder.ui.login.interactor

import android.app.Application
import android.util.Log
import io.reactivex.Observable
import io.realm.Realm
import ru.shtrm.familyfinder.data.database.AuthorizedUser
import ru.shtrm.familyfinder.data.database.repository.user.User
import ru.shtrm.familyfinder.data.network.*
import ru.shtrm.familyfinder.data.preferences.PreferenceHelper
import ru.shtrm.familyfinder.ui.base.interactor.BaseInteractor
import ru.shtrm.familyfinder.util.AppConstants
import java.util.*
import javax.inject.Inject

class LoginInteractor @Inject internal constructor(preferenceHelper: PreferenceHelper, apiHelper: ApiHelper, application: Application) : BaseInteractor(preferenceHelper, apiHelper, application), LoginMVPInteractor {

    override fun doServerLoginApiCall(email: String, password: String) =
            apiHelper.performServerLogin(LoginRequest.ServerLoginRequest(email = email, password = password))

    override fun updateUserInSharedPrefAfterLogin(user: User) =
            preferenceHelper.let {
                it.setCurrentUserId(user._id)
                it.setCurrentUserName(user.username)
                it.setCurrentUserEmail(user.login)
            }

    override fun getUserName(): String {
        return preferenceHelper.getCurrentUserName()
    }

    override fun getUserLogin(): String {
        return preferenceHelper.getCurrentUserEmail()
    }

    override fun makeTokenApiCall(userLogin: String): Observable<TokenResponse> =
            apiHelper.performTokenRequest(TokenRequest.SendTokenRequest(userLogin = userLogin))

    override fun checkUserLogin(userLogin: String): Boolean {
        val authUser = AuthorizedUser.instance
        val realm = Realm.getDefaultInstance()
        val user: User?
        if (userLogin != "")
            user = realm.where(User::class.java).equalTo("login", userLogin).findFirst()
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

    override fun sendTokenRequest(userLogin: String) {
        makeTokenApiCall(userLogin)
                .doOnError { t: Throwable ->
                    Log.e("performApiCall", t.message)
                    //sendTokenRequest(userLogin)
                }
                .subscribe({ tokenResponse ->
                    if (tokenResponse.statusCode == "0") {
                        Log.e("token", tokenResponse.token)
                        val authUser = AuthorizedUser.instance;
                        authUser.token = tokenResponse.token
                        if (!AuthorizedUser.instance.isAcraInit) {
                            initAcra()
                            AuthorizedUser.instance.isAcraInit = true
                        }
                    } else {
                        sendTokenRequest(userLogin)
                    }
                }, { err -> println(err) })
    }

    override fun updateUserInSharedPref(loginResponse: LoginResponse,
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

                preferenceHelper.let {
                    it.setCurrentUserId(loginResponse.userId)
                    it.setAccessToken(loginResponse.accessToken)
                    it.setCurrentUserLoggedInMode(loggedInMode)
                }
            }
        }
        realm.close()
    }
}