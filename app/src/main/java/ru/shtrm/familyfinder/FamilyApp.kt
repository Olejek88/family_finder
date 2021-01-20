package ru.shtrm.familyfinder

import android.app.Activity
import android.app.Application
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.realm.Realm
import okhttp3.OkHttpClient
import ru.shtrm.familyfinder.data.database.FamilyRealm
import ru.shtrm.familyfinder.di.component.DaggerAppComponent
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class FamilyApp : Application(), HasActivityInjector {

    @Inject
    internal lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = activityDispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)
        Realm.init(this)
        FamilyRealm.init(this)


        val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build()
    }
}