package ru.shtrm.familyfinder.ui.family.interactor

import io.realm.Realm
import io.realm.RealmResults
import ru.shtrm.familyfinder.data.database.repository.user.User
import ru.shtrm.familyfinder.data.network.ApiHelper
import ru.shtrm.familyfinder.data.preferences.PreferenceHelper
import ru.shtrm.familyfinder.ui.base.interactor.BaseInteractor
import javax.inject.Inject

class FamilyInteractor @Inject internal constructor(apiHelper: ApiHelper, preferenceHelper: PreferenceHelper) : BaseInteractor(apiHelper = apiHelper, preferenceHelper = preferenceHelper), FamilyMVPInterator {
    override fun alterInfo() {}

    override fun getUsers():RealmResults<User> {
        val realm = Realm.getDefaultInstance()
        return realm.where(User::class.java).findAll()
    }
}