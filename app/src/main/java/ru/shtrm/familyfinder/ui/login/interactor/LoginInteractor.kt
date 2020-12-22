package ru.shtrm.familyfinder.ui.login.interactor

import io.reactivex.Observable
import ru.shtrm.familyfinder.data.network.*
import ru.shtrm.familyfinder.data.preferences.PreferenceHelper
import ru.shtrm.familyfinder.ui.base.interactor.BaseInteractor
import ru.shtrm.familyfinder.util.AppConstants
import javax.inject.Inject

class LoginInteractor @Inject internal constructor(preferenceHelper: PreferenceHelper, apiHelper: ApiHelper) : BaseInteractor(preferenceHelper, apiHelper), LoginMVPInteractor {

    override fun doServerLoginApiCall(email: String, password: String) =
            apiHelper.performServerLogin(LoginRequest.ServerLoginRequest(email = email, password = password))

    override fun updateUserInSharedPref(loginResponse: LoginResponse, loggedInMode: AppConstants.LoggedInMode) =
            preferenceHelper.let {
                it.setCurrentUserId(loginResponse.userId)
                it.setAccessToken(loginResponse.accessToken)
                it.setCurrentUserLoggedInMode(loggedInMode)
            }

    override fun getUserName(): String {
        return preferenceHelper.getCurrentUserName()
    }

    override fun makeTokenApiCall(userLogin: String): Observable<TokenResponse> =
            apiHelper.performTokenRequest(TokenRequest.SendTokenRequest(userLogin = userLogin))

}