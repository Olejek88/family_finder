package ru.shtrm.familyfinder.ui.rate.interactor

import ru.shtrm.familyfinder.data.network.ApiHelper
import ru.shtrm.familyfinder.data.preferences.PreferenceHelper
import ru.shtrm.familyfinder.ui.base.interactor.BaseInteractor
import javax.inject.Inject

/**
 * Created by jyotidubey on 15/01/18.
 */
class RateUsInteractor @Inject internal constructor(apiHelper: ApiHelper, preferenceHelper: PreferenceHelper) : BaseInteractor(apiHelper = apiHelper, preferenceHelper = preferenceHelper), RateUsMVPInterator {

    override fun submitRating() {}
}