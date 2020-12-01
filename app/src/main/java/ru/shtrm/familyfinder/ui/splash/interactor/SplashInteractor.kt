package ru.shtrm.familyfinder.ui.splash.interactor

import android.content.Context
import ru.shtrm.familyfinder.data.network.ApiHelper
import ru.shtrm.familyfinder.data.preferences.PreferenceHelper
import ru.shtrm.familyfinder.ui.base.interactor.BaseInteractor
import javax.inject.Inject

class SplashInteractor @Inject constructor(private val mContext: Context, preferenceHelper: PreferenceHelper, apiHelper: ApiHelper) : BaseInteractor(preferenceHelper, apiHelper), SplashMVPInteractor {

}