package ru.shtrm.familyfinder.ui.feed.opensource.view

import ru.shtrm.familyfinder.data.network.OpenSource
import ru.shtrm.familyfinder.ui.base.view.MVPView

/**
 * Created by jyotidubey on 14/01/18.
 */
interface OpenSourceMVPView : MVPView {
    fun displayOpenSourceList(blogs: List<OpenSource>?)

}