package ru.shtrm.familyfinder.ui.login.interactor

import io.reactivex.Observable
import ru.shtrm.familyfinder.data.database.repository.user.User
import ru.shtrm.familyfinder.data.network.LoginResponse
import ru.shtrm.familyfinder.data.network.TokenResponse
import ru.shtrm.familyfinder.ui.base.interactor.MVPInteractor
import ru.shtrm.familyfinder.util.AppConstants

interface LoginMVPInteractor : MVPInteractor {

    fun doServerLoginApiCall(email: String, password: String): Observable<LoginResponse>

    fun updateUserInSharedPref(loginResponse: LoginResponse, loggedInMode: AppConstants.LoggedInMode)

    fun updateUserInSharedPrefAfterLogin(user: User)

    fun getUserName(): String

    fun getUserLogin(): String

    fun makeTokenApiCall(userLogin: String): Observable<TokenResponse>

    fun checkUserLogin(userLogin: String): Boolean

    fun sendTokenRequest(userLogin: String)

}