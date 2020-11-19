package ru.shtrm.familyfinder.ui.feed.opensource

import ru.shtrm.familyfinder.ui.feed.opensource.view.OpenSourceFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by jyotidubey on 14/01/18.
 */
@Module
internal abstract class OpenSourceFragmentProvider {

    @ContributesAndroidInjector(modules = [(OpenSourceFragmentModule::class)])
    internal abstract fun provideBlogFragmentFactory(): OpenSourceFragment

}