package ru.shtrm.familyfinder.di.builder

import ru.shtrm.familyfinder.ui.about.AboutFragmentProvider
import ru.shtrm.familyfinder.ui.feed.blog.BlogFragmentProvider
import ru.shtrm.familyfinder.ui.feed.opensource.OpenSourceFragmentProvider
import ru.shtrm.familyfinder.ui.feed.view.FeedActivity
import ru.shtrm.familyfinder.ui.login.LoginActivityModule
import ru.shtrm.familyfinder.ui.login.view.LoginActivity
import ru.shtrm.familyfinder.ui.main.MainActivityModule
import ru.shtrm.familyfinder.ui.main.view.MainActivity
import ru.shtrm.familyfinder.ui.rate.RateUsDialogFragmentProvider
import ru.shtrm.familyfinder.ui.splash.SplashActivityModule
import ru.shtrm.familyfinder.ui.splash.view.SplashMVPActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by jyotidubey on 05/01/18.
 */
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(SplashActivityModule::class)])
    abstract fun bindSplashActivity(): SplashMVPActivity

    @ContributesAndroidInjector(modules = [(MainActivityModule::class), (RateUsDialogFragmentProvider::class), (AboutFragmentProvider::class)])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [(LoginActivityModule::class)])
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [(BlogFragmentProvider::class), (OpenSourceFragmentProvider::class)])
    abstract fun bindFeedActivity(): FeedActivity

}