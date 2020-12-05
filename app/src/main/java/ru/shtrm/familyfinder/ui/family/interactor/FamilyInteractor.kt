package ru.shtrm.familyfinder.ui.family.interactor

import ru.shtrm.familyfinder.data.network.ApiHelper
import ru.shtrm.familyfinder.data.preferences.PreferenceHelper
import ru.shtrm.familyfinder.ui.base.interactor.BaseInteractor
import javax.inject.Inject

class FamilyInteractor @Inject internal constructor(apiHelper: ApiHelper, preferenceHelper: PreferenceHelper) : BaseInteractor(apiHelper = apiHelper, preferenceHelper = preferenceHelper), FamilyMVPInterator {
    override fun alterInfo() {}
}