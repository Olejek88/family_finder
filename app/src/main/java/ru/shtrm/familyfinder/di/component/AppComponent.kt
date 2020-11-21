package ru.shtrm.familyfinder.di.component

import android.app.Application
import ru.shtrm.familyfinder.FamilyApp
import ru.shtrm.familyfinder.di.builder.ActivityBuilder
import ru.shtrm.familyfinder.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(AndroidInjectionModule::class), (AppModule::class), (ActivityBuilder::class)])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: FamilyApp)

}