package ru.shtrm.familyfinder.ui.base.view

import android.widget.ProgressBar

interface MVPView {

    fun showProgress(progressBar : ProgressBar)

    fun hideProgress(progressBar: ProgressBar)

}