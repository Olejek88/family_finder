package ru.shtrm.familyfinder.ui.feed.opensource.interactor

import ru.shtrm.familyfinder.data.network.ApiHelper
import ru.shtrm.familyfinder.data.network.OpenSourceResponse
import ru.shtrm.familyfinder.data.preferences.PreferenceHelper
import ru.shtrm.familyfinder.ui.base.interactor.BaseInteractor
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by jyotidubey on 14/01/18.
 */
class OpenSourceInteractor @Inject internal constructor(preferenceHelper: PreferenceHelper, apiHelper: ApiHelper) : BaseInteractor(preferenceHelper, apiHelper), OpenSourceMVPInteractor {

    override fun getOpenSourceList(): Observable<OpenSourceResponse> {
        return apiHelper.getOpenSourceApiCall()
    }

}