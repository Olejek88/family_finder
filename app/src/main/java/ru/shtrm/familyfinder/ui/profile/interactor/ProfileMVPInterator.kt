package ru.shtrm.familyfinder.ui.profile.interactor

import android.content.Context
import io.reactivex.Observable
import ru.shtrm.familyfinder.data.database.repository.user.User
import ru.shtrm.familyfinder.data.network.SendResponse
import ru.shtrm.familyfinder.ui.base.interactor.MVPInteractor

interface ProfileMVPInterator : MVPInteractor {
    fun alterInfo(user: User): Observable<SendResponse>
    fun alterImage(user: User, context: Context)
}