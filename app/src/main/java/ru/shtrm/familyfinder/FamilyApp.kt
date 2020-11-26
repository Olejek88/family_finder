package ru.shtrm.familyfinder

import android.app.Activity
import android.app.Application
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import ru.shtrm.familyfinder.data.database.FamilyRealm
import ru.shtrm.familyfinder.di.component.DaggerAppComponent
import javax.inject.Inject

class FamilyApp : Application(), HasActivityInjector {

    @Inject
    lateinit internal var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = activityDispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        FamilyRealm.init(this,"family.realm")
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)
    }
}