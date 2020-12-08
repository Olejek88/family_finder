package ru.shtrm.familyfinder.ui.profile.presenter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import io.realm.Realm
import ru.shtrm.familyfinder.data.database.AuthorizedUser
import ru.shtrm.familyfinder.data.database.repository.user.User
import ru.shtrm.familyfinder.ui.base.presenter.BasePresenter
import ru.shtrm.familyfinder.ui.profile.interactor.ProfileMVPInterator
import ru.shtrm.familyfinder.ui.profile.view.ProfileFragmentMVPView
import ru.shtrm.familyfinder.util.FileUtils
import ru.shtrm.familyfinder.util.SchedulerProvider
import java.io.FileNotFoundException
import java.io.InputStream
import javax.inject.Inject

class ProfilePresenter<V : ProfileFragmentMVPView, I : ProfileMVPInterator> @Inject internal constructor(interator: I, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable) : BasePresenter<V, I>(interactor = interator, schedulerProvider = schedulerProvider, compositeDisposable = compositeDisposable), ProfileMVPPresenter<V, I> {

    override fun onImageClicked() = getView()?.let{
        //it.dismissDialog()
    }

    override fun onSubmitClicked() = interactor?.let {
/*
        it.submitRating()
        getView()?.let {
            it.showRatingSubmissionSuccessMessage()
            it.dismissDialog()
        }
*/
    }

    override fun storeImage(context: Context, data: Intent?): Bitmap? {
        if (data != null && data.data != null) {
            val inputStream: InputStream?
            try {
                inputStream = context.contentResolver.openInputStream(data.data!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                if (bitmap != null) {
                    val authUser = AuthorizedUser.instance
                    val imageName: String = authUser._id.toString().plus(".jpg")
                    val userBitmap: Bitmap? = FileUtils.storeNewImage(bitmap, context,
                            800, imageName)
                    authUser.image=imageName
                    val realm = Realm.getDefaultInstance()
                    realm.executeTransaction { realmB ->
                        val user = realmB.where(User::class.java).equalTo("login", authUser.login).findFirst()
                        user!!.image = imageName
                        Log.d("user",user.image)
                    }
                    return userBitmap
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
        return null
    }
}
