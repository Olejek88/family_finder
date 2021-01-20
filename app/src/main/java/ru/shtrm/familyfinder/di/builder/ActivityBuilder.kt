package ru.shtrm.familyfinder.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.shtrm.familyfinder.data.network.NetworkingClientModule
import ru.shtrm.familyfinder.ui.about.AboutFragmentProvider
import ru.shtrm.familyfinder.ui.family.FamilyFragmentProvider
import ru.shtrm.familyfinder.ui.login.LoginActivityModule
import ru.shtrm.familyfinder.ui.login.view.LoginActivity
import ru.shtrm.familyfinder.ui.main.MainActivityModule
import ru.shtrm.familyfinder.ui.main.view.MainActivity
import ru.shtrm.familyfinder.ui.map.MapFragmentProvider
import ru.shtrm.familyfinder.ui.profile.ProfileFragmentProvider
import ru.shtrm.familyfinder.ui.register.RegisterActivityModule
import ru.shtrm.familyfinder.ui.register.view.RegisterActivity

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(MainActivityModule::class), (FamilyFragmentProvider::class), (ProfileFragmentProvider::class), (AboutFragmentProvider::class), (MapFragmentProvider::class), (NetworkingClientModule::class)])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [(LoginActivityModule::class)])
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [(RegisterActivityModule::class)])
    abstract fun bindRegisterActivity(): RegisterActivity

}