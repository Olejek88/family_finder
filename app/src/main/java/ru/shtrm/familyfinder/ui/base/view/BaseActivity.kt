package ru.shtrm.familyfinder.ui.base.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ProgressBar
import dagger.android.AndroidInjection
import ru.shtrm.familyfinder.util.CommonUtil

abstract class BaseActivity : AppCompatActivity(), MVPView, BaseFragment.CallBack {

    override fun onCreate(savedInstanceState: Bundle?) {
        performDI()
        super.onCreate(savedInstanceState)
    }

    override fun hideProgress(progressBar: ProgressBar) {
        if (progressBar.visibility == View.VISIBLE) {
            progressBar.visibility = View.GONE
        }
    }

    override fun showProgress(progressBar: ProgressBar) {
        CommonUtil.showProgressBar(this.window, progressBar)
    }

    private fun performDI() = AndroidInjection.inject(this)

}