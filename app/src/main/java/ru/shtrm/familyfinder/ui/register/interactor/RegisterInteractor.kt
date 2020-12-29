package ru.shtrm.familyfinder.ui.register.interactor

import ru.shtrm.familyfinder.data.network.ApiHelper
import ru.shtrm.familyfinder.data.network.RegisterRequest
import ru.shtrm.familyfinder.data.network.RegisterResponse
import ru.shtrm.familyfinder.data.preferences.PreferenceHelper
import ru.shtrm.familyfinder.ui.base.interactor.BaseInteractor
import ru.shtrm.familyfinder.util.AppConstants
import javax.inject.Inject

class RegisterInteractor @Inject internal constructor(preferenceHelper: PreferenceHelper, apiHelper: ApiHelper) : BaseInteractor(preferenceHelper, apiHelper), RegisterMVPInteractor {

    override fun doServerRegisterApiCall(email: String, password: String, username: String) =
            apiHelper.performServerRegister(RegisterRequest.ServerRegisterRequest(email = email, password = password, username = username))

    override fun updateRegisterSharedPref(registerResponse: RegisterResponse, loggedInMode: AppConstants.LoggedInMode) =
            preferenceHelper.let {
                it.setCurrentUserId(registerResponse.userId)
                it.setAccessToken(registerResponse.accessToken)
                it.setCurrentUserEmail(registerResponse.userEmail)
                it.setCurrentUserName(registerResponse.userName)
                it.setCurrentUserLoggedInMode(loggedInMode)
            }

}