package ru.shtrm.familyfinder.ui.feed.blog.interactor

import ru.shtrm.familyfinder.data.network.BlogResponse
import ru.shtrm.familyfinder.ui.base.interactor.MVPInteractor
import io.reactivex.Observable

/**
 * Created by jyotidubey on 13/01/18.
 */
interface BlogMVPInteractor : MVPInteractor {

    fun getBlogList(): Observable<BlogResponse>

}