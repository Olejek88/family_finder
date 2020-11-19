package ru.shtrm.familyfinder.ui.feed.blog.view

import ru.shtrm.familyfinder.data.network.Blog
import ru.shtrm.familyfinder.ui.base.view.MVPView

/**
 * Created by jyotidubey on 13/01/18.
 */
interface BlogMVPView : MVPView {

    fun displayBlogList(blogs: List<Blog>?): Unit?

}