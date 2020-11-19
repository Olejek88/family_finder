package ru.shtrm.familyfinder.ui.feed.blog.interactor

import ru.shtrm.familyfinder.data.network.ApiHelper
import ru.shtrm.familyfinder.data.preferences.PreferenceHelper
import ru.shtrm.familyfinder.ui.base.interactor.BaseInteractor
import javax.inject.Inject

/**
 * Created by jyotidubey on 13/01/18.
 */
class BlogInteractor @Inject internal constructor(preferenceHelper: PreferenceHelper, apiHelper: ApiHelper) : BaseInteractor(preferenceHelper, apiHelper), BlogMVPInteractor {

    override fun getBlogList() = apiHelper.getBlogApiCall()

}