package ru.shtrm.familyfinder.ui.main.interactor

import ru.shtrm.familyfinder.data.network.LogoutResponse
import ru.shtrm.familyfinder.ui.base.interactor.MVPInteractor
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by jyotidubey on 08/01/18.
 */
interface MainMVPInteractor : MVPInteractor {

    fun getQuestionCardData(): Single<List<QuestionCardData>>
    fun getUserDetails(): Pair<String?, String?>
    fun makeLogoutApiCall(): Observable<LogoutResponse>
}