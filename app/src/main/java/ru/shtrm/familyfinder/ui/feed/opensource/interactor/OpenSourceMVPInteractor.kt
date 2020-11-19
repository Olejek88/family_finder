package ru.shtrm.familyfinder.ui.feed.opensource.interactor

import ru.shtrm.familyfinder.data.network.OpenSourceResponse
import ru.shtrm.familyfinder.ui.base.interactor.MVPInteractor
import io.reactivex.Observable

/**
 * Created by jyotidubey on 14/01/18.
 */
interface OpenSourceMVPInteractor : MVPInteractor {

    fun getOpenSourceList(): Observable<OpenSourceResponse>
}