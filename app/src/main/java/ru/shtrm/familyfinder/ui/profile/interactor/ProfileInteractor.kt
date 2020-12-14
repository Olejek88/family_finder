package ru.shtrm.familyfinder.ui.profile.interactor

import android.content.Context
import io.reactivex.Observable
import ru.shtrm.familyfinder.data.database.repository.user.User
import ru.shtrm.familyfinder.data.network.ApiHelper
import ru.shtrm.familyfinder.data.network.SendResponse
import ru.shtrm.familyfinder.data.network.UserRequest
import ru.shtrm.familyfinder.data.preferences.PreferenceHelper
import ru.shtrm.familyfinder.ui.base.interactor.BaseInteractor
import javax.inject.Inject

class ProfileInteractor @Inject internal constructor(apiHelper: ApiHelper, preferenceHelper: PreferenceHelper) : BaseInteractor(apiHelper = apiHelper, preferenceHelper = preferenceHelper), ProfileMVPInterator {

    override fun alterInfo(user: User): Observable<SendResponse> =
            apiHelper.performUserSendRequest(UserRequest.SendUserRequest(user = user))

    override fun alterImage(user: User, context: Context) =
            apiHelper.performUserImageSendRequest(UserRequest.SendImageRequest(user = user), context = context)

}