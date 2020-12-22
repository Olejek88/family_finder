package ru.shtrm.familyfinder.ui.login.interactor

import io.reactivex.Observable
import ru.shtrm.familyfinder.data.network.LoginResponse
import ru.shtrm.familyfinder.data.network.TokenResponse
import ru.shtrm.familyfinder.ui.base.interactor.MVPInteractor
import ru.shtrm.familyfinder.util.AppConstants

interface LoginMVPInteractor : MVPInteractor {

    fun doServerLoginApiCall(email: String, password: String): Observable<LoginResponse>

    fun updateUserInSharedPref(loginResponse: LoginResponse, loggedInMode: AppConstants.LoggedInMode)

    fun getUserName(): String

    fun makeTokenApiCall(userLogin: String): Observable<TokenResponse>
}