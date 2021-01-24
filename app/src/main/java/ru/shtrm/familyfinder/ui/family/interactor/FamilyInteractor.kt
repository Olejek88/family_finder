package ru.shtrm.familyfinder.ui.family.interactor

import android.app.Application
import io.reactivex.disposables.CompositeDisposable
import io.realm.Realm
import io.realm.RealmResults
import ru.shtrm.familyfinder.data.database.repository.user.User
import ru.shtrm.familyfinder.data.network.ApiHelper
import ru.shtrm.familyfinder.data.preferences.PreferenceHelper
import ru.shtrm.familyfinder.ui.base.interactor.BaseInteractor
import ru.shtrm.familyfinder.util.SchedulerProvider
import javax.inject.Inject

class FamilyInteractor @Inject internal constructor(apiHelper: ApiHelper, preferenceHelper: PreferenceHelper, application: Application, compositeDisposable: CompositeDisposable, schedulerProvider: SchedulerProvider) : BaseInteractor(apiHelper = apiHelper, preferenceHelper = preferenceHelper, application = application, compositeDisposable = compositeDisposable, schedulerProvider = schedulerProvider), FamilyMVPInterator {
    override fun alterInfo() {}

    override fun getUsers():RealmResults<User> {
        val realm = Realm.getDefaultInstance()
        return realm.where(User::class.java).findAll()
    }
}