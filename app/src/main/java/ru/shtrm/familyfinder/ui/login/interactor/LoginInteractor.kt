package ru.shtrm.familyfinder.ui.login.interactor

import ru.shtrm.familyfinder.data.network.*
import ru.shtrm.familyfinder.data.preferences.PreferenceHelper
import ru.shtrm.familyfinder.ui.base.interactor.BaseInteractor
import ru.shtrm.familyfinder.util.AppConstants
import javax.inject.Inject

class LoginInteractor @Inject internal constructor(preferenceHelper: PreferenceHelper, apiHelper: ApiHelper) : BaseInteractor(preferenceHelper, apiHelper), LoginMVPInteractor {

    override fun doServerLoginApiCall(email: String, password: String) =
            apiHelper.performServerLogin(LoginRequest.ServerLoginRequest(email = email, password = password))

    override fun doServerRegisterApiCall(email: String, password: String, username: String) =
            apiHelper.performServerRegister(RegisterRequest.ServerRegisterRequest(email = email, password = password, username = username))


    override fun updateUserInSharedPref(loginResponse: LoginResponse, loggedInMode: AppConstants.LoggedInMode) =
            preferenceHelper.let {
                it.setCurrentUserId(loginResponse.userId)
                it.setAccessToken(loginResponse.accessToken)
                it.setCurrentUserLoggedInMode(loggedInMode)
            }

    override fun updateRegisterSharedPref(registerResponse: RegisterResponse, loggedInMode: AppConstants.LoggedInMode) =
            preferenceHelper.let {
                it.setCurrentUserId(registerResponse.userId)
                it.setAccessToken(registerResponse.accessToken)
                it.setCurrentUserLoggedInMode(loggedInMode)
            }

}