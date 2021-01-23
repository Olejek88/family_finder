package ru.shtrm.familyfinder.ui.family.interactor

import io.realm.RealmResults
import ru.shtrm.familyfinder.data.database.repository.user.User
import ru.shtrm.familyfinder.ui.base.interactor.MVPInteractor

interface FamilyMVPInterator : MVPInteractor {
    fun alterInfo()
    fun getUsers(): RealmResults<User>
}