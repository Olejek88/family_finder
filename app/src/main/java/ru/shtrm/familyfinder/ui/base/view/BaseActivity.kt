package ru.shtrm.familyfinder.ui.base.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ProgressBar
import dagger.android.AndroidInjection
import ru.shtrm.familyfinder.util.CommonUtil

abstract class BaseActivity : AppCompatActivity(), MVPView, BaseFragment.CallBack {

    //private var progressDialog: ProgressDialog? = null
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        performDI()
        super.onCreate(savedInstanceState)
    }

//    override fun hideProgress() {
//        progressDialog?.let { if (it.isShowing) it.cancel() }
//    }

    override fun hideProgress() {
        if (progressBar != null && progressBar?.visibility == View.VISIBLE) {
            progressBar?.visibility == View.GONE
        }
    }

    override fun showProgress() {
        hideProgress()
        //progressDialog = CommonUtil.showLoadingDialog(this)
        progressBar = CommonUtil.showProgressBar(this, this.window)
    }

    private fun performDI() = AndroidInjection.inject(this)

}