package ru.shtrm.familyfinder.ui.map.interactor

import android.app.Application
import io.reactivex.disposables.CompositeDisposable
import ru.shtrm.familyfinder.data.network.ApiHelper
import ru.shtrm.familyfinder.data.preferences.PreferenceHelper
import ru.shtrm.familyfinder.ui.base.interactor.BaseInteractor
import ru.shtrm.familyfinder.util.SchedulerProvider
import javax.inject.Inject

class MapInteractor @Inject internal constructor(apiHelper: ApiHelper, preferenceHelper: PreferenceHelper, application: Application, compositeDisposable: CompositeDisposable, schedulerProvider: SchedulerProvider) : BaseInteractor(apiHelper = apiHelper, preferenceHelper = preferenceHelper, application = application, compositeDisposable = compositeDisposable, schedulerProvider = schedulerProvider), MapMVPInteractor {

}