package ru.shtrm.familyfinder.ui.main.interactor

import ru.shtrm.familyfinder.data.database.repository.options.OptionsRepo
import ru.shtrm.familyfinder.data.database.repository.questions.QuestionRepo
import ru.shtrm.familyfinder.data.network.ApiHelper
import ru.shtrm.familyfinder.data.preferences.PreferenceHelper
import ru.shtrm.familyfinder.ui.base.interactor.BaseInteractor
import javax.inject.Inject

class MainInteractor @Inject internal constructor(private val questionRepoHelper: QuestionRepo, private val optionsRepoHelper: OptionsRepo, preferenceHelper: PreferenceHelper, apiHelper: ApiHelper) : BaseInteractor(preferenceHelper = preferenceHelper, apiHelper = apiHelper), MainMVPInteractor {
    override fun getUserDetails() = Pair(preferenceHelper.getCurrentUserName(),
            preferenceHelper.getCurrentUserEmail())

    override fun makeLogoutApiCall() = apiHelper.performLogoutApiCall()

}


