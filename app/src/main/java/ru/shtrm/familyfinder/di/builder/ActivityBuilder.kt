package ru.shtrm.familyfinder.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.shtrm.familyfinder.ui.about.AboutFragmentProvider
import ru.shtrm.familyfinder.ui.feed.blog.BlogFragmentProvider
import ru.shtrm.familyfinder.ui.feed.opensource.OpenSourceFragmentProvider
import ru.shtrm.familyfinder.ui.feed.view.FeedActivity
import ru.shtrm.familyfinder.ui.login.LoginActivityModule
import ru.shtrm.familyfinder.ui.login.view.LoginActivity
import ru.shtrm.familyfinder.ui.main.MainActivityModule
import ru.shtrm.familyfinder.ui.main.view.MainActivity
import ru.shtrm.familyfinder.ui.map.MapFragmentProvider
import ru.shtrm.familyfinder.ui.profile.ProfileFragmentProvider
import ru.shtrm.familyfinder.ui.register.RegisterActivityModule
import ru.shtrm.familyfinder.ui.register.view.RegisterActivity
import ru.shtrm.familyfinder.ui.splash.SplashActivityModule
import ru.shtrm.familyfinder.ui.splash.view.SplashMVPActivity

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(SplashActivityModule::class)])
    abstract fun bindSplashActivity(): SplashMVPActivity

    @ContributesAndroidInjector(modules = [(MainActivityModule::class), (ProfileFragmentProvider::class), (AboutFragmentProvider::class), (MapFragmentProvider::class)])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [(LoginActivityModule::class)])
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [(RegisterActivityModule::class)])
    abstract fun bindRegisterActivity(): RegisterActivity

    @ContributesAndroidInjector(modules = [(BlogFragmentProvider::class), (OpenSourceFragmentProvider::class)])
    abstract fun bindFeedActivity(): FeedActivity

}