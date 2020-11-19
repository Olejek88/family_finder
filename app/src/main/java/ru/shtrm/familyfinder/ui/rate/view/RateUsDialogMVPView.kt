package ru.shtrm.familyfinder.ui.rate.view

import ru.shtrm.familyfinder.ui.base.view.MVPView

/**
 * Created by jyotidubey on 14/01/18.
 */
interface RateUsDialogMVPView : MVPView {

    fun dismissDialog()
    fun showRatingSubmissionSuccessMessage()
}