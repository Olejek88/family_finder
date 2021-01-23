package ru.shtrm.familyfinder.ui.map.interactor

import android.app.Application
import ru.shtrm.familyfinder.data.network.ApiHelper
import ru.shtrm.familyfinder.data.preferences.PreferenceHelper
import ru.shtrm.familyfinder.ui.base.interactor.BaseInteractor
import javax.inject.Inject

class MapInteractor @Inject internal constructor(apiHelper: ApiHelper, preferenceHelper: PreferenceHelper, application: Application) : BaseInteractor(apiHelper = apiHelper, preferenceHelper = preferenceHelper, application = application), MapMVPInteractor {

}