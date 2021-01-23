package ru.shtrm.familyfinder.ui.profile.interactor

import android.app.Application
import android.content.Context
import io.reactivex.Observable
import io.realm.Realm
import ru.shtrm.familyfinder.data.database.AuthorizedUser
import ru.shtrm.familyfinder.data.database.repository.user.User
import ru.shtrm.familyfinder.data.network.ApiHelper
import ru.shtrm.familyfinder.data.network.SendResponse
import ru.shtrm.familyfinder.data.network.UserRequest
import ru.shtrm.familyfinder.data.preferences.PreferenceHelper
import ru.shtrm.familyfinder.ui.base.interactor.BaseInteractor
import javax.inject.Inject

class ProfileInteractor @Inject internal constructor(apiHelper: ApiHelper, preferenceHelper: PreferenceHelper, application: Application) : BaseInteractor(apiHelper = apiHelper, preferenceHelper = preferenceHelper, application = application), ProfileMVPInterator {

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
            //presenter.sendUserRequest(realm.copyFromRealm(user), "bearer ".plus(authUser.token))
        }
        realm.close()
    }

    private fun sendUserImageRequest(user: User, context: Context, bearer: String) {
    }
}