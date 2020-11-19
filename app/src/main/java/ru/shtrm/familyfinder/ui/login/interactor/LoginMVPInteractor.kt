package ru.shtrm.familyfinder.ui.login.interactor

import ru.shtrm.familyfinder.data.network.LoginResponse
import ru.shtrm.familyfinder.ui.base.interactor.MVPInteractor
import ru.shtrm.familyfinder.util.AppConstants
import io.reactivex.Observable

/**
 * Created by jyotidubey on 10/01/18.
 */
interface LoginMVPInteractor : MVPInteractor {

    fun doServerLoginApiCall(email: String, password: String): Observable<LoginResponse>

    fun doFBLoginApiCall(): Observable<LoginResponse>

    fun doGoogleLoginApiCall(): Observable<LoginResponse>

    fun updateUserInSharedPref(loginResponse: LoginResponse, loggedInMode: AppConstants.LoggedInMode)

}