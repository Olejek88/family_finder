package ru.shtrm.familyfinder.ui.feed.blog.view

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.shtrm.familyfinder.R
import ru.shtrm.familyfinder.data.network.Blog
import ru.shtrm.familyfinder.ui.base.view.BaseFragment
import ru.shtrm.familyfinder.ui.feed.blog.interactor.BlogMVPInteractor
import ru.shtrm.familyfinder.ui.feed.blog.presenter.BlogMVPPresenter
import kotlinx.android.synthetic.main.fragment_blog.*
import javax.inject.Inject


/**
 * Created by jyotidubey on 13/01/18.
 */
class BlogFragment : BaseFragment(), BlogMVPView {

    companion object {

        fun newInstance(): BlogFragment {
            return BlogFragment()
        }
    }

    @Inject
    internal lateinit var blogAdapter: BlogAdapter
    @Inject
    internal lateinit var layoutManager: LinearLayoutManager
    @Inject
    internal lateinit var presenter: BlogMVPPresenter<BlogMVPView, BlogMVPInteractor>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflater.inflate(R.layout.fragment_blog, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.onAttach(this)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setUp() {
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        blog_recycler_view.layoutManager = layoutManager
        blog_recycler_view.itemAnimator = DefaultItemAnimator()
        blog_recycler_view.adapter = blogAdapter
        presenter.onViewPrepared()
    }

    override fun displayBlogList(blogs: List<Blog>?) = blogs?.let {
        blogAdapter.addBlogsToList(it)
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }
}