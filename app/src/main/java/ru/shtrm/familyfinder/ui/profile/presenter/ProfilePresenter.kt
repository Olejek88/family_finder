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
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

class ProfilePresenter<V : ProfileFragmentMVPView, I : ProfileMVPInterator> @Inject internal constructor(interator: I, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable) : BasePresenter<V, I>(interactor = interator, schedulerProvider = schedulerProvider, compositeDisposable = compositeDisposable), ProfileMVPPresenter<V, I> {

    override fun onImageClicked() = getView()?.let{
        //it.dismissDialog()
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
                        if (user != null) {
                            user.image = imageName
                            sendUserImageRequest(user, context, "bearer ".plus(authUser.token))
                        }
                    }
                    return userBitmap
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
        return null
    }

    override fun sendUserRequest(user: User, bearer: String) {
        interactor?.let {
            compositeDisposable.add(it.alterInfo(user, bearer)
                    .compose(schedulerProvider.ioToMainObservableScheduler())
                    .doOnError { t: Throwable ->
                        Log.e("performApiCall", t.message)
                    }
                    .subscribe({ tokenResponse ->
                        if (tokenResponse.statusCode === "0") {
                            // TODO change user here!
                        }
                    }, { err -> println(err) }))
        }
    }

    override fun sendUserImageRequest(user: User, context: Context, bearer: String) {
        interactor?.let {
            compositeDisposable.add(it.alterImage(user, context, bearer)
                    .compose(schedulerProvider.ioToMainObservableScheduler())
                    .doOnError { t: Throwable ->
                        Log.e("performApiCall", t.message)
                    }
                    .subscribe({ tokenResponse ->
                        if (tokenResponse.statusCode === "0") {
                            // TODO change user here!
                        } else {
                            Timer().schedule(60000) {
                                sendUserImageRequest(user,context, bearer)
                            }
                        }
                    }, { err -> println(err) }))
        }
    }
}
