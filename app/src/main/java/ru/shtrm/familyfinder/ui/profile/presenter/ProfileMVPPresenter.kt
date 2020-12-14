package ru.shtrm.familyfinder.ui.profile.presenter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import ru.shtrm.familyfinder.data.database.repository.user.User
import ru.shtrm.familyfinder.ui.base.presenter.MVPPresenter
import ru.shtrm.familyfinder.ui.profile.interactor.ProfileMVPInterator
import ru.shtrm.familyfinder.ui.profile.view.ProfileFragmentMVPView

interface ProfileMVPPresenter<V : ProfileFragmentMVPView, I : ProfileMVPInterator> : MVPPresenter<V, I> {
    fun onImageClicked(): Unit?
    fun storeImage(context: Context, data: Intent?): Bitmap?
    fun sendUserRequest(user: User)
    fun sendUserImageRequest(user: User, context: Context)
}