package ru.shtrm.familyfinder.ui.about.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.shtrm.familyfinder.R
import ru.shtrm.familyfinder.ui.base.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_about.*

/**
 * Created by jyotidubey on 12/01/18.
 */
class AboutFragment : BaseFragment() {

    companion object {

        internal val TAG = "AboutFragment"

        fun newInstance(): AboutFragment {
            return AboutFragment()
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_about, container, false)

    override fun setUp() = navBackBtn.setOnClickListener { getBaseActivity()?.onFragmentDetached(TAG) }

}