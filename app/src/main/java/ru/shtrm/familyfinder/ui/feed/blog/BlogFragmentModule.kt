package ru.shtrm.familyfinder.ui.feed.blog

import android.support.v7.widget.LinearLayoutManager
import ru.shtrm.familyfinder.ui.feed.blog.interactor.BlogInteractor
import ru.shtrm.familyfinder.ui.feed.blog.interactor.BlogMVPInteractor
import ru.shtrm.familyfinder.ui.feed.blog.presenter.BlogMVPPresenter
import ru.shtrm.familyfinder.ui.feed.blog.presenter.BlogPresenter
import ru.shtrm.familyfinder.ui.feed.blog.view.BlogAdapter
import ru.shtrm.familyfinder.ui.feed.blog.view.BlogFragment
import ru.shtrm.familyfinder.ui.feed.blog.view.BlogMVPView
import dagger.Module
import dagger.Provides
import java.util.*

/**
 * Created by jyotidubey on 14/01/18.
 */
@Module
class BlogFragmentModule {

    @Provides
    internal fun provideBlogInteractor(interactor: BlogInteractor): BlogMVPInteractor = interactor

    @Provides
    internal fun provideBlogPresenter(presenter: BlogPresenter<BlogMVPView, BlogMVPInteractor>)
            : BlogMVPPresenter<BlogMVPView, BlogMVPInteractor> = presenter

    @Provides
    internal fun provideBlogAdapter(): BlogAdapter = BlogAdapter(ArrayList())

    @Provides
    internal fun provideLinearLayoutManager(fragment: BlogFragment): LinearLayoutManager = LinearLayoutManager(fragment.activity)

}