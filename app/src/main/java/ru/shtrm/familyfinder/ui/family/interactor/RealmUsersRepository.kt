package ru.shtrm.familyfinder.ui.family.interactor

import io.reactivex.Flowable
import io.realm.Realm
import io.realm.RealmResults
import ru.shtrm.familyfinder.data.database.repository.user.User

class RealmUsersRepository {
    val users: Flowable<RealmResults<User>>
        get() {
            Realm.getDefaultInstance().use { realm ->
                val query = realm.where(User::class.java)
                val flowable: Flowable<RealmResults<User>>
                if (realm.isAutoRefresh) {
                    flowable = query.findAllAsync().asFlowable()
                } else {
                    flowable = Flowable.just(query.findAll())
                }
                return flowable
            }
        }
}