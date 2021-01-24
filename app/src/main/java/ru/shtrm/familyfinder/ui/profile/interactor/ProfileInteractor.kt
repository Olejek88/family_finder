package ru.shtrm.familyfinder.ui.profile.interactor

import android.app.Application
import android.content.Context
import android.util.Log
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.realm.Realm
import ru.shtrm.familyfinder.data.database.AuthorizedUser
import ru.shtrm.familyfinder.data.database.repository.user.User
import ru.shtrm.familyfinder.data.network.ApiHelper
import ru.shtrm.familyfinder.data.network.SendResponse
import ru.shtrm.familyfinder.data.network.UserRequest
import ru.shtrm.familyfinder.data.preferences.PreferenceHelper
import ru.shtrm.familyfinder.ui.base.interactor.BaseInteractor
import ru.shtrm.familyfinder.util.SchedulerProvider
import javax.inject.Inject

class ProfileInteractor @Inject internal constructor(apiHelper: ApiHelper, preferenceHelper: PreferenceHelper, application: Application, compositeDisposable: CompositeDisposable, schedulerProvider: SchedulerProvider) : BaseInteractor(apiHelper = apiHelper, preferenceHelper = preferenceHelper, application = application, compositeDisposable = compositeDisposable, schedulerProvider = schedulerProvider), ProfileMVPInterator {

    override fun alterInfo(user: User, bearer: String): Observable<SendResponse> =
            apiHelper.performUserSendRequest(UserRequest.SendUserRequest(user = user), bearer = bearer)

    override fun alterImage(user: User, context: Context, bearer: String) =
            apiHelper.performUserImageSendRequest(UserRequest.SendImageRequest(user = user), context = context, bearer = bearer)

    override fun storeImageInDb(imageName: String, context: Context): Boolean {
        val realm = Realm.getDefaultInstance()
        val authUser = AuthorizedUser.instance
        realm.executeTransaction { realmB ->
            val user = realmB.where(User::class.java).equalTo("login", authUser.login).findFirst()
            if (user != null) {
                user.image = imageName
                sendUserImageRequest(user, context, "bearer ".plus(authUser.token))
            }
        }
        realm.close()
        return true
    }

    override fun storeUsername(name: String) {
        val authUser = AuthorizedUser.instance
        authUser.username = name
        authUser.isSent = false

        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAsync { realmB ->
            val user = realmB.where(User::class.java).equalTo("login", authUser.login).findFirst()
            if (user != null) {
                user.username = name
                user.isSent = false
            }
        }
        val user = realm.where(User::class.java).equalTo("login", authUser.login).findFirst()
        if (user != null) {
            sendUserRequest(realm.copyFromRealm(user), "bearer ".plus(authUser.token))
        }
        realm.close()
    }

    override fun sendUserRequest(user: User, bearer: String) {
        let {
            compositeDisposable.add(it.alterInfo(user, bearer)
                    .compose(schedulerProvider.ioToMainObservableScheduler())
                    .doOnError { t: Throwable ->
                        Log.e("performApiCall", t.message)
                    }
                    .subscribe({ tokenResponse ->
                        if (tokenResponse.statusCode === "0") {
                            // TODO change user here!
                            AuthorizedUser.instance.isSent=true
                        }
                    }, {err ->
                        Log.e("performApiCall", err.message)
                    }))
        }
    }

    override fun sendUserImageRequest(user: User, context: Context, bearer: String) {
        let {
            compositeDisposable.add(it.alterImage(user, context, bearer)
                    .compose(schedulerProvider.ioToMainObservableScheduler())
                    .doOnError { t: Throwable ->
                        Log.e("performApiCall", t.message)
                    }
                    .subscribe({ tokenResponse ->
                        if (tokenResponse.statusCode === "0") {
                            // TODO change user here!
                            AuthorizedUser.instance.isImageSent=true
                        }
                    }, { err -> println(err) }))
        }
    }
}