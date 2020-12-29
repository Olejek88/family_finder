package ru.shtrm.familyfinder.ui.about

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.shtrm.familyfinder.ui.about.view.AboutFragment

@Module
abstract class AboutFragmentProvider {

    @ContributesAndroidInjector
    internal abstract fun provideAboutFragment(): AboutFragment

}