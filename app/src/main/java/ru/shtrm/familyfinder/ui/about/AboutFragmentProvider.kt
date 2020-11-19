package ru.shtrm.familyfinder.ui.about

import ru.shtrm.familyfinder.ui.about.view.AboutFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by jyotidubey on 15/01/18.
 */
@Module
abstract class AboutFragmentProvider {

    @ContributesAndroidInjector
    abstract internal fun provideAboutFragment(): AboutFragment

}