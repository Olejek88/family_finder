package ru.shtrm.familyfinder.ui.login.interactor

import ru.shtrm.familyfinder.data.network.LoginResponse
import ru.shtrm.familyfinder.ui.base.interactor.MVPInteractor
import ru.shtrm.familyfinder.util.AppConstants
import io.reactivex.Observable
import ru.shtrm.familyfinder.data.network.RegisterResponse

interface LoginMVPInteractor : MVPInteractor {

    fun doServerLoginApiCall(email: String, password: String): Observable<LoginResponse>

    fun doServerRegisterApiCall(email: String, password: String): Observable<RegisterResponse>

    fun updateUserInSharedPref(loginResponse: LoginResponse, loggedInMode: AppConstants.LoggedInMode)

}