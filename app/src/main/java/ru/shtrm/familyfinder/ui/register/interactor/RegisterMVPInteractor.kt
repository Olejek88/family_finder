package ru.shtrm.familyfinder.ui.register.interactor

import io.reactivex.Observable
import ru.shtrm.familyfinder.data.network.RegisterResponse
import ru.shtrm.familyfinder.ui.base.interactor.MVPInteractor
import ru.shtrm.familyfinder.util.AppConstants

interface RegisterMVPInteractor : MVPInteractor {

    fun doServerRegisterApiCall(email: String, password: String, username: String): Observable<RegisterResponse>

    fun updateRegisterSharedPref(registerResponse: RegisterResponse, loggedInMode: AppConstants.LoggedInMode)

}