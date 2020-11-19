package ru.shtrm.familyfinder.ui.feed.blog.presenter

import ru.shtrm.familyfinder.ui.base.presenter.BasePresenter
import ru.shtrm.familyfinder.ui.feed.blog.interactor.BlogMVPInteractor
import ru.shtrm.familyfinder.ui.feed.blog.view.BlogMVPView
import ru.shtrm.familyfinder.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by jyotidubey on 13/01/18.
 */
class BlogPresenter<V : BlogMVPView, I : BlogMVPInteractor> @Inject constructor(interactor: I, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable) : BasePresenter<V, I>(interactor = interactor, schedulerProvider = schedulerProvider, compositeDisposable = compositeDisposable), BlogMVPPresenter<V, I> {

    override fun onViewPrepared() {
        getView()?.showProgress()
        interactor?.let {
            it.getBlogList()
                    .compose(schedulerProvider.ioToMainObservableScheduler())
                    .subscribe { blogResponse ->
                        getView()?.let {
                            it.hideProgress()
                            it.displayBlogList(blogResponse.data)
                        }
                    }
        }
    }
}