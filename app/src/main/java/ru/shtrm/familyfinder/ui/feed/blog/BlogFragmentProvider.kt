package ru.shtrm.familyfinder.ui.feed.blog

import ru.shtrm.familyfinder.ui.feed.blog.view.BlogFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by jyotidubey on 14/01/18.
 */
@Module
internal abstract class BlogFragmentProvider {

    @ContributesAndroidInjector(modules = [BlogFragmentModule::class])
    internal abstract fun provideBlogFragmentFactory(): BlogFragment
}