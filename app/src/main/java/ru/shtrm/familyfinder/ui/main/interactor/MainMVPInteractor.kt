package ru.shtrm.familyfinder.ui.main.interactor

import io.reactivex.Observable
import ru.shtrm.familyfinder.data.network.LogoutResponse
import ru.shtrm.familyfinder.ui.base.interactor.MVPInteractor

interface MainMVPInteractor : MVPInteractor {

    fun getUserDetails(): Pair<String?, String?>
    fun makeLogoutApiCall(): Observable<LogoutResponse>

}